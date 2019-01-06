package com.scnu.eureka.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * 注册中心dataStyle
 */
@EnableEurekaServer
@SpringBootApplication
public class Main {
	public static void main(String[] args){
		SpringApplication.run(Main.class,args);
	}
}
