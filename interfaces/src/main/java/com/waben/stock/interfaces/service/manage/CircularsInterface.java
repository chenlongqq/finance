package com.waben.stock.interfaces.service.manage;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.waben.stock.interfaces.dto.manage.CircularsDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.CircularsQuery;
import com.waben.stock.interfaces.pojo.query.PageInfo;

/**
 * 通告 reference服务接口
 *
 * @author luomengan
 */
@FeignClient(name = "manage", path = "circulars", qualifier = "circularsInterface")
public interface CircularsInterface {
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	Response<List<CircularsDto>> fetchCirculars(@RequestParam(value = "enable",required = false) Boolean enable);

	@RequestMapping(value = "/pages", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<PageInfo<CircularsDto>> pages(@RequestBody CircularsQuery query);
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	Response<CircularsDto> fetchById(@PathVariable("id") Long id);

	@RequestMapping(value = "/modify", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<CircularsDto> modify(@RequestBody CircularsDto circularsDto);

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	void delete(@PathVariable("id") Long id);

	@RequestMapping(value = "/save", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<CircularsDto> add(CircularsDto requestDto);
}
