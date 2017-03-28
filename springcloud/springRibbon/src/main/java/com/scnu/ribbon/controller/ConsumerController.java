package com.scnu.ribbon.controller;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {
	@Autowired
    RestTemplate restTemplate;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
    	int a = RandomUtils.nextInt(100);
    	int b = RandomUtils.nextInt(50);
        return restTemplate.getForEntity("http://compute-service/add?a=" + a + "&b=" + b , String.class).getBody();
    }
}
