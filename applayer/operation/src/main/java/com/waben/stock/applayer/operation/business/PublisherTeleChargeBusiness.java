package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.activity.PublisherTeleChargeDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.activity.PublisherTeleChargeInterface;

@Service
public class PublisherTeleChargeBusiness {

	@Autowired
	@Qualifier("publisherTeleChargeInterface")
	private PublisherTeleChargeInterface publisherTeleChargeService;

	public PageInfo<PublisherTeleChargeDto> pages(int pageNo, Integer pageSize) {
		Response<PageInfo<PublisherTeleChargeDto>> response = publisherTeleChargeService
				.getPublisherTeleChargeList(pageNo, pageSize);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public Void isPay(long id) {
		Response<Void> response = publisherTeleChargeService.setPay(id);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
}
