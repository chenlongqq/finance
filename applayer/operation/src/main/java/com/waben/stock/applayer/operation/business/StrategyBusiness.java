package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.buyrecord.BuyRecordDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyHoldingQuery;
import com.waben.stock.interfaces.pojo.query.StrategyPostedQuery;
import com.waben.stock.interfaces.pojo.query.StrategyUnwindQuery;
import com.waben.stock.interfaces.service.buyrecord.BuyRecordInterface;

@Service
public class StrategyBusiness {
    @Autowired
    @Qualifier("buyRecordInterface")
    private BuyRecordInterface buyRecordService;

    public PageInfo<BuyRecordDto> postedPages(StrategyPostedQuery strategyPostedQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByPostedQuery(strategyPostedQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<BuyRecordDto> holdingPages(StrategyHoldingQuery strategyHoldingQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByHoldingQuery(strategyHoldingQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


    public PageInfo<BuyRecordDto> unwindPages(StrategyUnwindQuery strategyUnwindQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByUnwindQuery(strategyUnwindQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PageInfo<BuyRecordDto> withdrawPages(StrategyUnwindQuery strategyUnwindQuery) {
        Response<PageInfo<BuyRecordDto>> response = buyRecordService.pagesByWithdrawQuery(strategyUnwindQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
