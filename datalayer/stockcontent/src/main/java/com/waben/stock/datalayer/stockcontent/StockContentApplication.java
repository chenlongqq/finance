package com.waben.stock.datalayer.stockcontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
public class StockContentApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockContentApplication.class, args);
	}
}
