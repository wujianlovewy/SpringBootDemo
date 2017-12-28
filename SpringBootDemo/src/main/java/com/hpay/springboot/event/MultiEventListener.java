package com.hpay.springboot.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.AbstractApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

@Component("applicationEventMulticaster")
public class MultiEventListener extends AbstractApplicationEventMulticaster{

	private Executor taskExecutor = Executors.newFixedThreadPool(5);
	
	public MultiEventListener(BeanFactory beanFactory){
		setBeanFactory(beanFactory);
	}
	
	@Override
	public void multicastEvent(ApplicationEvent event) {
		this.multicastEvent(event, this.resolveDefaultEventType(event));
	}

	@Override
	public void multicastEvent(final ApplicationEvent event, ResolvableType eventType) {
		ResolvableType type = (eventType != null ? eventType : resolveDefaultEventType(event));
		for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
			Executor executor = getTaskExecutor();
			if (executor != null) {
				//System.out.println("启用全局异步事件方式event=["+event+"], listener=["+listener.toString()+"]...");
				executor.execute(new Runnable() {
					@Override
					public void run() {
						invokeListener(listener, event);
					}
				});
			}else {
				System.out.println("启用同步事件方式event=["+event+"], listener=["+listener.toString()+"]...");
				//dinvokeListener(listener, event);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void invokeListener(ApplicationListener listener,
			ApplicationEvent event) {
		listener.onApplicationEvent(event);
	}

	private ResolvableType resolveDefaultEventType(ApplicationEvent event) {
		return ResolvableType.forInstance(event);
	}

	public Executor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(Executor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
}
