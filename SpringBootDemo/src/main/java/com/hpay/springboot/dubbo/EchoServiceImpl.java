package com.hpay.springboot.dubbo;

import com.alibaba.dubbo.config.annotation.Service;

//@Service(version="1.0.0")
public class EchoServiceImpl implements EchoService {

	@Override
	public String echo(String str) {
		System.out.println(str);
		return str;
	}

}
