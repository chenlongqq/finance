package com.waben.stock.applayer.operation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
//服务发现客户端
@EnableDiscoveryClient
//服务调用
@EnableFeignClients(basePackages = { "com.waben.stock" })
//断路器
@EnableHystrix
//扫描包
@EnableScheduling
@EnableWebSocket
@ComponentScan(basePackages = { "com.waben.stock" })
public class OperationApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationApplication.class, args);
	}
}
