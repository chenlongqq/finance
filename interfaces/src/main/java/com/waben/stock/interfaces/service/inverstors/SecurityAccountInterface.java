package com.waben.stock.interfaces.service.inverstors;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.investor.SecurityAccountDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.SecurityAccountQuery;

/**
 * @author Created by yuyidi on 2017/11/30.
 * @desc
 */
@FeignClient(name = "investors", path = "securityaccount", qualifier = "securityAccountInterface")
public interface SecurityAccountInterface {

	@RequestMapping(value = "/pages", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Response<PageInfo<SecurityAccountDto>> pagesByQuery(@RequestBody SecurityAccountQuery securityAccountQuery);

	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	Response<SecurityAccountDto> fetchById(@PathVariable("id") Long id);

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	void delete(@PathVariable("id") Long id);
}
