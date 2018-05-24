package com.waben.stock.datalayer.financial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
public class FinancialApplication {

	static Logger logger = LoggerFactory.getLogger(FinancialApplication.class);

	public static void main(String[] args) {
		logger.info("系统启动成功");
		SpringApplication.run(FinancialApplication.class, args);
	}
}
