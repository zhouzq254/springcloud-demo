package com.scnu.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class ComputeController {

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        Integer r = a + b;
        log.info("a+b={}" ,r);
        return r;
    }
}