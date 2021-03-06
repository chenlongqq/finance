package com.waben.stock.datalayer.buyrecord.business;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.publisher.CapitalAccountDto;
import com.waben.stock.interfaces.dto.publisher.FrozenCapitalDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.publisher.CapitalAccountInterface;

@Service
public class CapitalAccountBusiness {

	@Autowired
	@Qualifier("capitalAccountInterface")
	private CapitalAccountInterface service;

	public CapitalAccountDto fetchByPublisherId(Long publisherId) {
		Response<CapitalAccountDto> response = service.fetchByPublisherId(publisherId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto serviceFeeAndReserveFund(Long publisherId, Long buyRecordId, BigDecimal serviceFee,
			BigDecimal reserveFund, BigDecimal deferredFee) {
		Response<CapitalAccountDto> response = service.serviceFeeAndReserveFund(publisherId, buyRecordId, serviceFee,
				reserveFund, deferredFee);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public FrozenCapitalDto fetchFrozenCapital(Long publisherId, Long buyRecordId) {
		Response<FrozenCapitalDto> response = service.fetchFrozenCapital(publisherId, buyRecordId);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto deferredCharges(Long publisherId, Long buyRecordId, BigDecimal deferredCharges) {
		Response<CapitalAccountDto> response = service.deferredCharges(publisherId, buyRecordId, deferredCharges);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto returnReserveFund(Long publisherId, Long buyRecordId, String buyRecordSerialCode,
			BigDecimal profitOrLoss) {
		Response<CapitalAccountDto> response = service.returnReserveFund(publisherId, buyRecordId, buyRecordSerialCode,
				profitOrLoss);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto returnDeferredFee(Long publisherId, Long buyRecordId, BigDecimal deferredFee) {
		Response<CapitalAccountDto> response = service.returnDeferredFee(publisherId, buyRecordId, deferredFee);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto revoke(Long publisherId, Long buyRecordId, BigDecimal serviceFee, BigDecimal deferredFee) {
		Response<CapitalAccountDto> response = service.revoke(publisherId, buyRecordId, serviceFee, deferredFee);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

	public CapitalAccountDto revision(CapitalAccountDto capitalAccountDto) {
		Response<CapitalAccountDto> response = service.modifyCapitalAccount(capitalAccountDto);
		if ("200".equals(response.getCode())) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
