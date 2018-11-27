package com.scnu.ribbon.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
/**
 * @SpringBootApplication
 * 等效@Configuration, @EnableAutoConfiguration and @ComponentScan
 * 在此处如果不加上@ComponentScan("com.scnu.ribbon")
 * 输入url访问时会出现404
 * @author zzq
 *
 */
@ComponentScan("com.scnu.ribbon")
@SpringBootApplication
@EnableDiscoveryClient
public class SpringRibbon {
	//@LoadBalanced 客户端负载均衡
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringRibbon.class, args);
	}
}
