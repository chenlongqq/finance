package com.waben.stock.applayer.operation.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.StaffDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.StaffQuery;
import com.waben.stock.interfaces.service.manage.StaffInterface;

/**
 * @author Created by yuyidi on 2017/11/19.
 * @desc
 */

@Service
public class StaffBusiness {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("staffInterface")
    private StaffInterface staffService;

    public PageInfo<StaffDto> staffs(StaffQuery staffQuery) {
        Response<PageInfo<StaffDto>> response = staffService.pagesByQuery(staffQuery);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StaffDto fetchById(Long id) {
        Response<StaffDto> response = staffService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StaffDto findById(Long id) {
        Response<StaffDto> response = staffService.fetchById(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StaffDto revision(StaffDto requestDto) {
        Response<StaffDto> response = staffService.modify(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        staffService.delete(id);
    }

    public StaffDto save(StaffDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        Response<StaffDto> response = staffService.saveStaff(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public StaffDto modif(StaffDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        requestDto.setUpdateTime(new Date());
        Response<StaffDto> response = staffService.saveStaff(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
