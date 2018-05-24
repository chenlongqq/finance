package com.waben.stock.datalayer.investors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
//服务调用
@EnableFeignClients(basePackages = { "com.waben.stock" })
//断路器
@EnableHystrix
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
@SpringBootApplication
public class InvestorsApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvestorsApplication.class, args);
	}
}
