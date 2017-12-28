package com.hpay.springboot.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener implements ApplicationListener<MessageEvent> {

	@Override
	public void onApplicationEvent(MessageEvent event) {
		System.out.println("线程["+Thread.currentThread().getId()+"] 接收事件:"+event);
	}

}
