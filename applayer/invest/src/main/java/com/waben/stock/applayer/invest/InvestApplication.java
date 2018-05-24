package com.waben.stock.applayer.invest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
public class InvestApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestApplication.class, args);
	}
}
