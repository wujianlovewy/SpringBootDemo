package com.hpay.springboot.rocketmq;

import io.dubbo.springboot.DubboAutoConfiguration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration  
@ComponentScan({"com.hpay.springboot.rocketmq"})  
@Import({DubboAutoConfiguration.class} )  
//@ImportResource("classpath:config/*.xml")  
@SpringBootApplication  
public class RocketMQServer {
}
