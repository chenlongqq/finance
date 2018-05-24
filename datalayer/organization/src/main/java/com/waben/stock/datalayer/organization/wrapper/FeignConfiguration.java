package com.waben.stock.datalayer.organization.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignFormatterRegistrar;
import org.springframework.format.FormatterRegistry;

import com.waben.stock.datalayer.organization.wrapper.formatter.DateFormatter;


/**
 * @author Created by yuyidi on 2017/11/8.
 * @desc
 */
//@Configuration
public class FeignConfiguration implements FeignFormatterRegistrar {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new DateFormatter());
    }

}
