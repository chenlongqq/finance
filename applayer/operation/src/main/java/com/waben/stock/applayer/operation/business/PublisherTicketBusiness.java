package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.activity.PublisherTicketInterface;

@Service
public class PublisherTicketBusiness {
    @Autowired
    @Qualifier("publisherTicketInterface")
    private PublisherTicketInterface publisherTicketService;

    public PageInfo<PublisherTicketDto> pages(int pageNo, Integer pageSize) {
        Response<PageInfo<PublisherTicketDto>> response = publisherTicketService.getPublisherTicketList(pageNo, pageSize);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }
}
