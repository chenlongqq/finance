package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;
import com.waben.stock.interfaces.service.inverstors.SecurityAccountInterface;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@Service
public class SecurityAccountBusiness {

    @Autowired
    @Qualifier("securityAccountInterface")
    private SecurityAccountInterface securityAccountService;

    public PageInfo<SecurityAccountDto> securityAccounts(SecurityAccountQuery securityAccountQuery) {
        Response<PageInfo<SecurityAccountDto>> response = securityAccountService.pagesByQuery(securityAccountQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public SecurityAccountDto findById(Long id){
        Response<SecurityAccountDto> response = securityAccountService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        securityAccountService.delete(id);
    }
}
