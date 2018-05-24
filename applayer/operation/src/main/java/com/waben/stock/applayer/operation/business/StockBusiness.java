package com.waben.stock.applayer.operation.business;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StockDto;
import com.waben.stock.interfaces.dto.stockcontent.StockExponentDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StockQuery;
import com.waben.stock.interfaces.service.stockcontent.StockExponentInterface;
import com.waben.stock.interfaces.service.stockcontent.StockInterface;


/**
 * @author Created by yuyidi on 2017/11/22.
 * @desc
 */
@Service
public class StockBusiness {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("stockInterface")
    private StockInterface stockService;

    @Autowired
    @Qualifier("stockExponentInterface")
    private StockExponentInterface stockExponentService;

    public PageInfo<StockDto> pages(StockQuery stockQuery) {
        Response<PageInfo<StockDto>> response = stockService.pagesByQuery(stockQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockDto fetchWithExponentByCode(String stockCode) {
        Response<StockDto> response = stockService.fetchWithExponentByCode(stockCode);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockDto fetchById(Long id) {
        Response<StockDto> response = stockService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public Integer revision(StockDto stockDto) {
        Response<Integer> response = stockService.modify(stockDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        stockService.delete(id);
    }

    public List<StockExponentDto> fetchStockExponents() {
        Response<List<StockExponentDto>> response = stockExponentService.fetchStockExponents();
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StockDto save(StockDto requestDto) {
        Response<StockDto> response = stockService.add(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
