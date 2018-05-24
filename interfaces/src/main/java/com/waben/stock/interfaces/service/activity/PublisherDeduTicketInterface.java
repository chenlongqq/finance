package com.waben.stock.interfaces.service.activity;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.activity.PublisherDeduTicketDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 
 * 
 * @author guowei 2018/4/11
 *
 */
@FeignClient(name = "activity", path = "publisherdeduticket", qualifier = "publisherDeduTicketInterface")
public interface PublisherDeduTicketInterface {

	@RequestMapping(value = "/savePublisherDeduTicket", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PublisherDeduTicketDto> savePublisherDeduTicket(
			@RequestBody PublisherDeduTicketDto publisherDeduTicketDto);

	@RequestMapping(value = "/getPublisherDeduTicketList", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<PublisherDeduTicketDto>> getPublisherDeduTicketList(@RequestParam(value = "pageno") int pageno,
			@RequestParam(value = "pagesize") Integer pagesize);

	@RequestMapping(value = "/getPublisherDeduTicket/{publisherDeduTicketId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Response<PublisherDeduTicketDto> getPublisherDeduTicket(
			@PathVariable("publisherDeduTicketId") long publisherDeduTicketId);

	@RequestMapping(value = "/getPublisherDeduTicketByApId/{apId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Response<List<PublisherDeduTicketDto>> getPublisherDeduTicketsByApId(@PathVariable("apId") long apId);
}
