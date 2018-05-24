package com.waben.stock.applayer.operation.warpper;

import com.waben.stock.interfaces.exception.ExecptionHandler;
import com.waben.stock.interfaces.warpper.converter.DateConverter;
import com.waben.stock.interfaces.warpper.converter.UniversalEnumConverterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @author Created by yuyidi on 2017/6/27.
 * @desc
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Autowired
    private ExecptionHandler execptionHandler;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addConverterFactory(new UniversalEnumConverterFactory());
        registry.addConverter(new DateConverter());
    }

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(execptionHandler);
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }



}
