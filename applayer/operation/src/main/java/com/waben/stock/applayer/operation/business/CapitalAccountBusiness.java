package com.waben.stock.applayer.operation.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.constants.ExceptionConstant;
import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.exception.NetflixCircuitException;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CapitalAccountQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountInterface")
	private CapitalAccountInterface capitalAccountService;

	public PageInfo<CapitalAccountDto> pages(CapitalAccountQuery query) {
		Response<PageInfo<CapitalAccountDto>> response = capitalAccountService.pages(query);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto findByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = capitalAccountService.fetchByPublisherId(publisherId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto findById(Long capitalAccountId) {
		Response<CapitalAccountDto> response = capitalAccountService.fetchById(capitalAccountId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto modifyCapitalAccount(CapitalAccountDto capitalAccountDto) {
		Response<CapitalAccountDto> response = capitalAccountService.modifyCapitalAccount(capitalAccountDto);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto recharge(Long publisherId, BigDecimal amount, Long rechargeId) {
		Response<CapitalAccountDto> response = capitalAccountService.recharge(publisherId, amount, rechargeId);
		String code = response.getCode();
		if ("200".equals(code)) {
			return response.getResult();
		} else if (ExceptionConstant.NETFLIX_CIRCUIT_EXCEPTION.equals(code)) {
			throw new NetflixCircuitException(code);
		}
		throw new ServiceException(response.getCode());
	}

}
