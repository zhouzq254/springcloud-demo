package com.scnu.zipkin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@EnableZipkinServer
@SpringBootApplication
public class ZipkinMain {
    public static void main(String[] args){
        SpringApplication.run(ZipkinMain.class,args);
    }
}
