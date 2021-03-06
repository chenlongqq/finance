package com.waben.stock.interfaces.service.buyrecord;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.buyrecord.DeferredRecordDto;
import com.waben.stock.interfaces.pojo.Response;

/**
 * 递延记录 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "buyrecord", path = "deferredrecord", qualifier = "deferredRecordInterface")
public interface DeferredRecordInterface {

	@RequestMapping(value = "/deferredrecord/{publisherId}/{buyRecordId}", method = RequestMethod.GET)
	public Response<List<DeferredRecordDto>> fetchByPublisherIdAndBuyRecordId(
			@PathVariable("publisherId") Long publisherId, @PathVariable("buyRecordId") Long buyRecordId);

}
