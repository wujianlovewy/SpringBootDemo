package com.hpay.springboot.rocketmq;

import java.util.List;

import io.netty.channel.ChannelOutboundBuffer.MessageProcessor;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RocketMQConsumerConfiguration {
	public static final Logger LOGGER = LoggerFactory.getLogger(RocketMQConsumerConfiguration.class);
	
	@Autowired
    private RocketMQConfig rocketMQConfig;

    @Bean
    public DefaultMQPushConsumer getRocketMQConsumer() throws Exception {
    	String groupName = rocketMQConfig.getGroupName();
    	String namesrvAddr = this.rocketMQConfig.getNamesrvAddr();
    	String topic = this.rocketMQConfig.getTopic();
    	String tag = this.rocketMQConfig.getTagName();
    	
        if (StringUtils.isBlank(groupName)){
            throw new Exception("groupName is null !!!");
        }
        if (StringUtils.isBlank(namesrvAddr)){
            throw new Exception("namesrvAddr is null !!!");
        }
        if (StringUtils.isBlank(topic)){
            throw new Exception("topic is null !!!");
        }
        if (StringUtils.isBlank(tag)){
            throw new Exception("tag is null !!!");
        }
        
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
					ConsumeConcurrentlyContext context) {
				
				 System.out.println(Thread.currentThread().getName()
	                        + " Receive New Messages: " + msgs);
				 
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
        try {
            consumer.subscribe(topic, tag);
            consumer.start();
            LOGGER.info("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",groupName,topic,namesrvAddr);
        }catch (MQClientException e){
            LOGGER.error("consumer is start !!! groupName:{},topic:{},namesrvAddr:{}",groupName,topic,namesrvAddr,e);
            throw new Exception(e);
        }
        return consumer;
    }
}
