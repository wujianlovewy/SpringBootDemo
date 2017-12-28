package com.hpay.springboot.test;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hpay.springboot.rocketmq.RocketMQProducerConfiguration;
import com.hpay.springboot.rocketmq.RocketMQServer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=RocketMQServer.class)
public class RocketmqTest {
	
	@Autowired
	private DefaultMQProducer defaultMQProducer;

	@Test
	public void test(){
		try{
			defaultMQProducer.send(new Message("TEST", "TagA", "Hello RocketMQ".getBytes())); 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
