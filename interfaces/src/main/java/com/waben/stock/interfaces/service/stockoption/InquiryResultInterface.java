package com.waben.stock.interfaces.service.stockoption;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockoption.InquiryResultDto;
import com.waben.stock.interfaces.pojo.Response;

@FeignClient(name = "stockoption", path = "inquiryresult", qualifier = "inquiryResultInterface")
public interface InquiryResultInterface {
	@RequestMapping(value = "/add", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<InquiryResultDto> add(@RequestBody InquiryResultDto inquiryResultDto);

	@RequestMapping(value = "/findByTrade/{trade}", method = RequestMethod.GET)
	Response<InquiryResultDto> findByTrade(@PathVariable("trade") Long trade);
}
