package com.scnu.ribbon.controller;

import com.scnu.ribbon.aop.ServiceAspect;
import com.scnu.ribbon.config.ApplicationContextUtils;
import com.scnu.ribbon.dto.User;
import org.apache.commons.lang.math.RandomUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author zzq
 *
 */
@RestController
public class ConsumerController {
	
	private Logger logger = Logger.getLogger(ConsumerController.class);
	@Autowired
    RestTemplate restTemplate;

	@Autowired
    private ApplicationContextUtils applicationContextUtils;
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
    	int a = RandomUtils.nextInt(100);
    	int b = RandomUtils.nextInt(50);
    	logger.info("a="+a + ",b="+b);
        return restTemplate.getForEntity("http://compute-service/add?a=" + a + "&b=" + b , String.class).getBody();
    }

    @PostMapping("/test")
    public String test(@Validated @RequestBody User user){

        System.out.println(applicationContextUtils.getBean(ServiceAspect.class));
        return user.toString();
    }




}
