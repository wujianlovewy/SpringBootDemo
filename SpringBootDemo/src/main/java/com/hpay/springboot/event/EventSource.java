package com.hpay.springboot.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
public class EventSource implements ApplicationEventPublisherAware {

	private ApplicationEventPublisher publisher;
	
	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void publishEvent(ApplicationEvent event){
		this.publisher.publishEvent(event);
	}

}
