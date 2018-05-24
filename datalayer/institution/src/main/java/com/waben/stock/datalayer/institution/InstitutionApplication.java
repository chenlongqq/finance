package com.waben.stock.datalayer.institution;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
//扫描包
@ComponentScan(basePackages = { "com.waben.stock" })
@SpringBootApplication
public class InstitutionApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstitutionApplication.class, args);
	}
}
