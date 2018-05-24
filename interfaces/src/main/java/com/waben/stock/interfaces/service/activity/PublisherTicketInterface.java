package com.waben.stock.interfaces.service.activity;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.activity.PublisherTicketDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 
 * 
 * @author guowei 2018/4/11
 *
 */
@FeignClient(name = "activity", path = "publisherticket", qualifier = "publisherTicketInterface")
public interface PublisherTicketInterface {

	/**
	 * 保存优惠券
	 * 
	 * @param publisherTicketDto
	 * @return
	 */
	@RequestMapping(value = "/savePublisherTicket", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PublisherTicketDto> savePublisherTicket(@RequestBody PublisherTicketDto publisherTicketDto);

	/**
	 * 获取优惠券列表
	 * 
	 * @param pageno
	 * @param pagesize
	 * @return
	 */
	@RequestMapping(value = "/getPublisherTicketList", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<PublisherTicketDto>> getPublisherTicketList(@RequestParam(value = "pageno") int pageno,
			@RequestParam(value = "pagesize") Integer pagesize);

	@RequestMapping(value = "/getPublisherTicket/{publisherTicketId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Response<PublisherTicketDto> getPublisherTicket(@PathVariable("publisherTicketId") long publisherTicketId);

	@RequestMapping(value = "/getPublisherTicketByApId/{apId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Response<List<PublisherTicketDto>> getPublisherTicketsByApId(@PathVariable("apId") long apId);
}
