package com.waben.stock.applayer.operation.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.waben.stock.applayer.operation.business.PaymentOrderBusiness;
import com.waben.stock.interfaces.dto.publisher.PaymentOrderDto;
import com.waben.stock.interfaces.pojo.Response;
import com.waben.stock.interfaces.pojo.query.PageInfo;
import com.waben.stock.interfaces.pojo.query.PaymentOrderQuery;

@Controller
@RequestMapping("/aliturn")
public class AliturnController {

	@Autowired
	private PaymentOrderBusiness paymentOrderBusiness;

	@RequestMapping("/index")
	public String index() {
		return "publisher/aliturn/index";
	}

	@PostMapping("/pages")
	@ResponseBody
	public Response<PageInfo<PaymentOrderDto>> pages(@RequestBody PaymentOrderQuery query) {
		return new Response<>(paymentOrderBusiness.pagesByQuery(query));
	}

	@PostMapping("/paid")
	@ResponseBody
	public Response<PaymentOrderDto> aliturnPaid(@RequestParam(required = true) String paymentNo) {
		return new Response<>(paymentOrderBusiness.aliturnPaid(paymentNo));
	}

	@PostMapping("/partpaid")
	@ResponseBody
	public Response<PaymentOrderDto> aliturnPartPaid(@RequestParam(required = true) String paymentNo,
			@RequestParam(required = true) BigDecimal partAmount) {
		return new Response<>(paymentOrderBusiness.aliturnPartPaid(paymentNo, partAmount));
	}

}
