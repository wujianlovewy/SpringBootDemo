package com.hpay.springboot.shiro;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration  
@EnableCaching
@ComponentScan({"com.hpay.springboot.shiro"})  
@SpringBootApplication 
public class ShiroServer {

}
