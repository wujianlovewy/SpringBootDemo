package com.hpay.springboot.rocketmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

@Component
@PropertySource({"classpath:rocketmq.properties","classpath:application.properties"})
public class RocketMQConfig {

	@Value("${rocketmq.groupName}")
	private String groupName;
	
	@Value("${rocketmq.namesrvAddr}")
	private String namesrvAddr;
	
	@Value("${rocketmq.instanceName}")
	private String instanceName;
	
	@Value("${rocketmq.topic}")
	private String topic;
	
	@Value("${rocketmq.tagName}")
	private String tagName;
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigure() {
        return new PropertySourcesPlaceholderConfigurer();
    }

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public void setNamesrvAddr(String namesrvAddr) {
		this.namesrvAddr = namesrvAddr;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}
