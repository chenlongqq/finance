package com.waben.stock.applayer.tactics.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.waben.stock.interfaces.dto.organization.OrganizationDto;
import com.waben.stock.interfaces.exception.ServiceException;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.service.organization.OrganizationInterface;

/**
 * 机构 Business
 *
 * @author luomengan
 */
@Service
public class OrganizationBusiness {

	@Autowired
	@Qualifier("organizationInterface")
	private OrganizationInterface reference;

	public OrganizationDto fetchByCode(String code) {
		Response<OrganizationDto> response = reference.fetchByCode(code);
		if (response.getCode().equals("200")) {
			return response.getResult();
		}
		throw new ServiceException(response.getCode());
	}

}
