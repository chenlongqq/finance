package com.waben.stock.datalayer.stockoption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//服务发现客户端
@EnableDiscoveryClient
//服务调用
@EnableFeignClients(basePackages = { "com.waben.stock" })
// 扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
public class StockOptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockOptionApplication.class, args);
	}
}
