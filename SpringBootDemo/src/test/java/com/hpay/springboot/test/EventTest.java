package com.hpay.springboot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hpay.springboot.event.EventSource;
import com.hpay.springboot.event.MessageEvent;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=com.hpay.springboot.dubbo.Server.class)
public class EventTest {

	@Autowired
	private EventSource eventSource;
	
	@Test
	public void testEvent(){
		this.eventSource.publishEvent(new MessageEvent(new Object(), "事件发生了")); 
	}
}
