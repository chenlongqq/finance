package com.waben.stock.applayer.operation.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.manage.CircularsInterface;

/**
 * @author Created by yuyidi on 2017/12/11.
 * @desc
 */
@Service
public class CircularsBusiness {

	@Autowired
	@Qualifier("circularsInterface")
	private CircularsInterface circularsService;

	public PageInfo<CircularsDto> pages(CircularsQuery query) {
		Response<PageInfo<CircularsDto>> response = circularsService.pages(query);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CircularsDto fetchById(Long id) {
		Response<CircularsDto> response = circularsService.fetchById(id);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public void delete(Long id) {
		circularsService.delete(id);
	}

	public CircularsDto revision(CircularsDto requestDto) {
		Response<CircularsDto> response = circularsService.modify(requestDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CircularsDto save(CircularsDto requestDto) {
		Response<CircularsDto> response = circularsService.add(requestDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}
}
