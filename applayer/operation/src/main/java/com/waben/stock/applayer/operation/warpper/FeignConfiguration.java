package com.waben.stock.applayer.operation.warpper;


import com.waben.stock.applayer.operation.warpper.formatter.DateFormatter;
import com.waben.stock.interfaces.warpper.converter.DateConverter;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;


/**
 * @author Created by yuyidi on 2017/11/8.
 * @desc
 */
@Component
public class FeignConfiguration implements FeignFormatterRegistrar {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void registerFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new DateFormatter());
    	registry.addConverter(String.class, Date.class, new DateConverter());
    }

}
