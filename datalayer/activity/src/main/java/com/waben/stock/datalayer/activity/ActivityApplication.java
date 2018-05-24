package com.waben.stock.datalayer.activity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDiscoveryClient
//服务调用
@EnableFeignClients(basePackages = { "com.waben.stock" })
//断路器
@EnableHystrix
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
@SpringBootApplication
@EnableScheduling
public class ActivityApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ActivityApplication.class, args);
	}
	
}
