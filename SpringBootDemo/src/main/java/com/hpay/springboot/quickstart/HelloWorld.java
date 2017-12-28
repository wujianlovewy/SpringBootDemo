package com.hpay.springboot.quickstart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hpay.springboot.dubbo.EchoService;

@SpringBootApplication
@RestController
public class HelloWorld {
	
	@Reference(version="1.0.0")
	private EchoService echoservice;
	
	@RequestMapping("/hello")
	public String hello(){
		echoservice.echo("hello");
		return "HelloWorld";
	}
	
}
