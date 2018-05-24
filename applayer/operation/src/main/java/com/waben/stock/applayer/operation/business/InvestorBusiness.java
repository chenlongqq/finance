package com.waben.stock.applayer.operation.business;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.applayer.operation.warpper.messagequeue.rabbitmq.EntrustApplyProducer;
import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.dto.investor.InvestorDto;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.enums.BuyRecordState;
import com.waben.stock.interfaces.enums.EntrustState;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.InvestorQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust;
import com.waben.stock.interfaces.service.inverstors.InvestorInterface;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class InvestorBusiness {
    @Autowired
    @Qualifier("investorInterface")
    private InvestorInterface investorService;
    @Autowired
    private StockBusiness stockBusiness;
    @Autowired
    private EntrustApplyProducer entrustProducer;
    @Autowired
    private BuyRecordBusiness buyRecordBusiness;
    public PageInfo<InvestorDto> investors(InvestorQuery investorQuery) {
        Response<PageInfo<InvestorDto>> response = investorService.pagesByQuery(investorQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    /***
    * @author yuyidi 2017-12-02 20:38:31
    * @method buyRecordEntrust
     * @param investorDto
     * @param buyRecordDto
    * @return com.waben.stock.interfaces.pojo.stock.SecuritiesStockEntrust
    * @description 根据投资人及点买信息初始化券商股票委托信息并委托买入或卖出
    */
    private SecuritiesStockEntrust buyRecordEntrust(InvestorDto investorDto, BuyRecordDto buyRecordDto, BigDecimal entrustPrice) {
        SecuritiesStockEntrust securitiesStockEntrust = new SecuritiesStockEntrust();
        securitiesStockEntrust.setBuyRecordId(buyRecordDto.getId());
        securitiesStockEntrust.setSerialCode(buyRecordDto.getSerialCode());
        securitiesStockEntrust.setInvestor(investorDto.getId());
        StockDto stockDto = stockBusiness.fetchWithExponentByCode(buyRecordDto.getStockCode());
        securitiesStockEntrust.setStockName(stockDto.getName());
        securitiesStockEntrust.setStockCode(stockDto.getCode());
        securitiesStockEntrust.setExponent(stockDto.getExponent().getExponentCode());
        securitiesStockEntrust.setEntrustNumber(buyRecordDto.getNumberOfStrand());
        securitiesStockEntrust.setEntrustPrice(entrustPrice);
        securitiesStockEntrust.setBuyRecordState(buyRecordDto.getState());
        return securitiesStockEntrust;
    }

    public SecuritiesStockEntrust buyIn(InvestorDto investorDto,BuyRecordDto buyRecordDto) {
        SecuritiesStockEntrust securitiesStockEntrust= buyRecordEntrust(investorDto, buyRecordDto,buyRecordDto.getDelegatePrice());
        //TODO 若没有接收到响应请求， 则回滚服务业务
        Response<BuyRecordDto> response = investorService.stockApplyBuyIn(investorDto.getId(), securitiesStockEntrust,
                investorDto.getSecuritiesSession());
        String code = response.getCode();
        if ("200".equals(code)) {
            BuyRecordDto result= response.getResult();
            if (response.getResult().getState().equals(BuyRecordState.BUYLOCK)) {
                securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
                securitiesStockEntrust.setTradeNo(result.getTradeNo());
                securitiesStockEntrust.setEntrustNo(result.getDelegateNumber());
                securitiesStockEntrust.setEntrustState(EntrustState.HASBEENREPORTED);
                securitiesStockEntrust.setEntrustTime(result.getUpdateTime());
                entrustProducer.entrustApplyBuyIn(securitiesStockEntrust);
                return securitiesStockEntrust;
            }
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public SecuritiesStockEntrust sellOut(InvestorDto investorDto,BuyRecordDto buyRecordDto,BigDecimal entrustPrice) {
        SecuritiesStockEntrust securitiesStockEntrust= buyRecordEntrust(investorDto, buyRecordDto,entrustPrice);
        //申请卖出，更新数据库
        Response<BuyRecordDto> response = investorService.stockApplySellOut(investorDto.getId(), securitiesStockEntrust,
                investorDto.getSecuritiesSession());
        String code = response.getCode();
        if ("200".equals(code)) {
            securitiesStockEntrust.setTradeSession(investorDto.getSecuritiesSession());
            securitiesStockEntrust.setTradeNo(response.getResult().getTradeNo());
            securitiesStockEntrust.setEntrustNo(response.getResult().getDelegateNumber());
            securitiesStockEntrust.setEntrustState(EntrustState.HASBEENSUCCESS);
            securitiesStockEntrust.setEntrustTime(response.getResult().getUpdateTime());
            entrustProducer.entrustApplySellOut(securitiesStockEntrust);
            return securitiesStockEntrust;
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public List<InvestorDto> findAllInvestors(){
    	Response<List<InvestorDto>> response = investorService.fetchAllInvestors();
    	String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public InvestorDto findById(Long id){
        Response<InvestorDto> response = investorService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Integer revision(InvestorDto requestDto) {
        Response<Integer> response = investorService.modify(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
    public void delete(Long id) {
        investorService.delete(id);
    }

    public InvestorDto findByUserName(String userName) {
        Response<InvestorDto> response = investorService.fetchByUserName(userName);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
