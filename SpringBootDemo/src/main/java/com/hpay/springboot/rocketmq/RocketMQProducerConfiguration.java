package com.hpay.springboot.rocketmq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class RocketMQProducerConfiguration {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(RocketMQProducerConfiguration.class);
	
    @Autowired
    private RocketMQConfig rocketMQConfig;

    @Bean
    public DefaultMQProducer getRocketMQProducer() throws Exception {
        
    	if (StringUtils.isBlank(rocketMQConfig.getGroupName())) {
            throw new Exception("groupName is blank");
        }
        if (StringUtils.isBlank(rocketMQConfig.getNamesrvAddr())) {
            throw new Exception("nameServerAddr is blank");
        }
        if (StringUtils.isBlank(rocketMQConfig.getInstanceName())){
            throw new Exception("instanceName is blank");
        }
        DefaultMQProducer producer;
        producer = new DefaultMQProducer(rocketMQConfig.getGroupName());
        producer.setNamesrvAddr(rocketMQConfig.getNamesrvAddr());
        //producer.setInstanceName(rocketMQConfig.getInstanceName());
        try {
            producer.start();
            LOGGER.info(String.format("producer is start ! groupName:[%s],namesrvAddr:[%s]"
                    , rocketMQConfig.getGroupName(), rocketMQConfig.getNamesrvAddr()));
        } catch (MQClientException e) {
            LOGGER.error(String.format("producer is error {}"
                    , e.getMessage(),e));
            throw new Exception(e);
        }
        return producer;
    }
}
