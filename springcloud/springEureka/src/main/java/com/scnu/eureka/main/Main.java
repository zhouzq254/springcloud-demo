package com.scnu.eureka.main;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		new SpringApplicationBuilder(Main.class).web(true).run(args);
	}
}
