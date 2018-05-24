package com.waben.stock.interfaces.service.stockoption;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.waben.stock.interfaces.dto.stockoption.OfflineStockOptionTradeDto;
import com.waben.stock.interfaces.pojo.Response;

@FeignClient(name = "stockoption", path = "offlinestockoptiontrade", qualifier = "offlineStockOptionTradeInterface")
public interface OfflineStockOptionTradeInterface {
	@RequestMapping(value = "/add", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	Response<OfflineStockOptionTradeDto> add(@RequestBody OfflineStockOptionTradeDto offlineStockOptionTradeDto);

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	Response<OfflineStockOptionTradeDto> find(@PathVariable("id") Long id);

	@RequestMapping(value = "/monthsProfit/{year}", method = RequestMethod.GET)
	Response<List<OfflineStockOptionTradeDto>> fetchMonthsProfit(@PathVariable("year") String year);

	@RequestMapping(value = "/settlement/{id}/{sellingPrice}", method = RequestMethod.PUT)
	Response<OfflineStockOptionTradeDto> settlement(@PathVariable("id") Long id,
			@PathVariable("sellingPrice") BigDecimal sellingPrice);

	@RequestMapping(value = "/exercise/{id}", method = RequestMethod.PUT)
	Response<OfflineStockOptionTradeDto> exercise(@PathVariable("id") Long id);
}
