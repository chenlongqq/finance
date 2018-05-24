package com.waben.stock.applayer.operation.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.PermissionDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PermissionQuery;
import com.waben.stock.interfaces.service.manage.PermissionInterface;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Service
public class PermissionBusiness {

    @Autowired
    @Qualifier("permissionInterface")
    private PermissionInterface permissionService;

    public PageInfo<PermissionDto> pages(PermissionQuery query) {
        Response<PageInfo<PermissionDto>> response = permissionService.pages(query);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        permissionService.delete(id);
    }

    public List<PermissionDto> fetchPermissions() {
        Response<List<PermissionDto>> response = permissionService.fetchPermissionsByVariety(1L);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        } else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PermissionDto fetchById(Long id) {
        Response<PermissionDto> response = permissionService.permission(id);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PermissionDto revision(PermissionDto requestDto) {
        Response<PermissionDto> response = permissionService.modify(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public PermissionDto save(PermissionDto requestDto) {
        Response<PermissionDto> response = permissionService.add(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
