package com.waben.stock.applayer.operation.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.stockcontent.StrategyTypeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StrategyTypeQuery;
import com.waben.stock.interfaces.service.stockcontent.StrategyTypeInterface;

/**
 * @author Created by yuyidi on 2017/12/6.
 * @desc
 */
@Service
public class StrategyTypeBusiness {

    @Autowired
    @Qualifier("strategyTypeInterface")
    private StrategyTypeInterface strategyTypeService;

    public PageInfo<StrategyTypeDto> pages(StrategyTypeQuery query) {
        Response<PageInfo<StrategyTypeDto>> response = strategyTypeService.pages(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }


    public StrategyTypeDto fetchById(Long id) {
        Response<StrategyTypeDto> response = strategyTypeService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StrategyTypeDto revision(StrategyTypeDto requestDto, List<Long> loss) {
        Response<StrategyTypeDto> response = strategyTypeService.modify(requestDto,loss);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        strategyTypeService.delete(id);
    }

    public StrategyTypeDto save(StrategyTypeDto requestDto) {
        Response<StrategyTypeDto> response = strategyTypeService.add(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
