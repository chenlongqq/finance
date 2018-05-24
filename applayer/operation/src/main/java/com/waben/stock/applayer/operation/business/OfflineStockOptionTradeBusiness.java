package com.waben.stock.applayer.operation.business;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.dto.stockoption.StockOptionTradeDto;
import com.waben.stock.interfaces.enums.OfflineStockOptionTradeState;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.stockoption.OfflineStockOptionTradeInterface;

@Service
public class OfflineStockOptionTradeBusiness {
    @Autowired
    @Qualifier("offlineStockOptionTradeInterface")
    private OfflineStockOptionTradeInterface offlineStockOptionTradeService;
    @Autowired
    private StockOptionTradeBusiness stockOptionTradeBusiness;
    public OfflineStockOptionTradeDto add(OfflineStockOptionTradeDto offlineStockOptionTradeDto) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.add(offlineStockOptionTradeDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto settlement(Long id, BigDecimal sellingPrice) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.settlement(id, sellingPrice);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        StockOptionTradeDto stockOptionTrade = stockOptionTradeBusiness.findById(id);
        if(OfflineStockOptionTradeState.APPLYRIGHT.equals(stockOptionTrade.getStatus())) {
            stockOptionTradeBusiness.modify(id);
        }

        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto find(Long id) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.find(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Map<String,BigDecimal> findMonthsProfit(String year) {
        SimpleDateFormat sdf = new SimpleDateFormat( "MM" );
        Response<List<OfflineStockOptionTradeDto>> response = offlineStockOptionTradeService.fetchMonthsProfit(year);
        String code = response.getCode();
        if ("200".equals(code)) {
            List<OfflineStockOptionTradeDto> result = response.getResult();
            Map<String,BigDecimal> map = new TreeMap<>();
            for(int i=1;i<=12;i++) {
                if(i<10) {
                    map.put("0"+i,new BigDecimal("0"));
                }else {
                    map.put(i+"",new BigDecimal("0"));
                }
            }
            for(OfflineStockOptionTradeDto dto : result) {
                String month = sdf.format(dto.getSellingTime());
                map.put(month,map.get(month).add(dto.getProfit()));
            }
            return map;
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public OfflineStockOptionTradeDto exercise(Long id) {
        Response<OfflineStockOptionTradeDto> response = offlineStockOptionTradeService.exercise(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
