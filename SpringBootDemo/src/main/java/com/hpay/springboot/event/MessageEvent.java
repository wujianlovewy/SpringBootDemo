package com.hpay.springboot.event;

import org.springframework.context.ApplicationEvent;

public class MessageEvent extends ApplicationEvent{

	private static final long serialVersionUID = -4751067367285240688L;

	private String msg;
	
	public MessageEvent(Object source, String msg){
		this(source);
		this.msg = msg;
	}
	
	public MessageEvent(Object source) {
		super(source);
	}

	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		return "MessageEvent [msg=" + msg + "]";
	}

}
