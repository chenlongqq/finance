package com.waben.stock.applayer.operation.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.BuyRecordQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;

/**
 * @author Created by yuyidi on 2017/12/2.
 * @desc
 */
@Service
public class BuyRecordBusiness {

    @Autowired
    @Qualifier("buyRecordInterface")
    private BuyRecordInterface buyRecordService;


    public BuyRecordDto fetchBuyRecord(Long buyRecord) {
        Response<BuyRecordDto> response = buyRecordService.fetchBuyRecord(buyRecord);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<BuyRecordDto> pages(BuyRecordQuery buyRecordQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByQuery(buyRecordQuery);
        if ("200".equals(response.getCode())) {
            return response.getResult();
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        buyRecordService.delete(id);
    }

    public Map<String,BigDecimal> findMonthsProfit(String year) {
        SimpleDateFormat sdf = new SimpleDateFormat( "MM" );
        Response<List<BuyRecordDto>> response = buyRecordService.fetchMonthsProfit(year);
        String code = response.getCode();
        if ("200".equals(code)) {
            List<BuyRecordDto> result = response.getResult();
            Map<String,BigDecimal> map = new TreeMap<>();
            for(int i=1;i<=12;i++) {
                if(i<10) {
                    map.put("0"+i,new BigDecimal("0"));
                }else {
                    map.put(i+"",new BigDecimal("0"));
                }
            }
            for(BuyRecordDto dto : result) {
                String month = sdf.format(dto.getSellingTime());
                map.put(month,map.get(month).add(dto.getSettlement().getInvestorProfitOrLoss()));
            }
            return map;
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Map<String,Object> fetchBuyRecordProfitAndPosition() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Map<String,Object> map = new HashMap<>();
        BigDecimal todayProfit = new BigDecimal(0);
        BigDecimal allProfit = new BigDecimal(0);
        int todayCount = 0;
        int allCount = 0;
        List<BuyRecordDto> resultProfit = buyRecordService.buyRecordsWithStatus(6).getResult();
        for(BuyRecordDto buyRecordDto : resultProfit) {
            if(buyRecordDto.getSettlement()!=null) {
                if(sdf.format(new Date()).equals(sdf.format(buyRecordDto.getUpdateTime()))) {
                    todayProfit = todayProfit.add(buyRecordDto.getSettlement().getInvestorProfitOrLoss());
                }
                allProfit = allProfit.add(buyRecordDto.getSettlement().getInvestorProfitOrLoss());
            }
        }
        List<BuyRecordDto> resultPosition = buyRecordService.buyRecordsWithStatus(3).getResult();
        for(BuyRecordDto buyRecordDto : resultPosition) {
            if(buyRecordDto.getSettlement()!=null) {
                if(sdf.format(new Date()).equals(sdf.format(buyRecordDto.getUpdateTime()))) {
                    todayCount++;
                }
            }
            allCount++;
        }
        map.put("todayProfit",todayProfit);
        map.put("allProfit",allProfit);
        map.put("todayCount",todayCount);
        map.put("allCount",allCount);
        return map;
    }
}
