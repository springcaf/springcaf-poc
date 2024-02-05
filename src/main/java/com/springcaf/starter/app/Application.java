package com.springcaf.starter.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication @ComponentScan(basePackages = { "com.springcaf.starter.app", "com.springcaf.starter.feature.controller", "com.springcaf.starter.feature.dataservice.rest.controller"} )
public class Application extends SpringBootServletInitializer{

    public static void main(String[] args) throws Throwable {
    	SpringApplication.run(Application.class, args);
        
    }

}
