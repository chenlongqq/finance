package com.waben.stock.interfaces.interceptor;

import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author Created by yuyidi on 2017/11/27.
 * @desc
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {
	
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("feign", "true");
    }
}
