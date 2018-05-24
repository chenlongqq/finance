package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.message.MessagingDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.MessagingQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.message.MessagingInterface;

/**
 * 
 * @author Created by hujian on 2018年1月4日
 */
@Service
public class MessagingBusiness {

	@Autowired
	@Qualifier("messagingInterface")
	private MessagingInterface messagingService;
	
	public MessagingDto addMessaging(MessagingDto messagingDto){
		Response<MessagingDto> response = messagingService.addMessaging(messagingDto);
		String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
		
	}
	
	public Long dropMessaging(Long messagingId){
		Response<Long> response = messagingService.dropMessaging(messagingId);
		String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
		
	}
	
	public MessagingDto modifyMessaging(MessagingDto messagingDto){
		Response<MessagingDto> response = messagingService.modifyMessaging(messagingDto);
		String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
		
	}
	
	public MessagingDto fetchMessagingById(Long messagingId){
		Response<MessagingDto> response = messagingService.fetchMessagingById(messagingId);
		String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
		
	}
	
	public PageInfo<MessagingDto> pages(MessagingQuery messagingQuery){
		Response<PageInfo<MessagingDto>> response = messagingService.pages(messagingQuery);
		String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
	}

    public MessagingDto save(MessagingDto requestDto) {
        Response<MessagingDto> response = messagingService.addMessaging(requestDto);
        String code = response.getCode();
        if ("200".equals(code)) {
            return response.getResult();
        }else if(ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)){
            throw new NetflixCircuitException(code);
        }
        throw new ServiceException(response.getCode());
    }

    public void delete(Long id) {
        messagingService.dropMessaging(id);
    }
}
