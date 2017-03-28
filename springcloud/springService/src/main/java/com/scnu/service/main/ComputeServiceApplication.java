package com.scnu.service.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
@ComponentScan("com.scnu.service")
@EnableDiscoveryClient
@SpringBootApplication
public class ComputeServiceApplication {
	public static void main(String[] args){
		new SpringApplicationBuilder(ComputeServiceApplication.class).web(true).run(args);
	}

}
