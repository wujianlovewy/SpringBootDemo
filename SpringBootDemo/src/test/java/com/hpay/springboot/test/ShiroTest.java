package com.hpay.springboot.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hpay.springboot.shiro.ShiroProperties;
import com.hpay.springboot.shiro.ShiroServer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes=ShiroServer.class)
@EnableCaching
@SpringBootTest
@AutoConfigureMockMvc
public class ShiroTest {

	@Autowired
	private ShiroProperties shiroProperties;
	
	@Autowired  
	private MockMvc mvc;
	
	@Test
	public void test(){
		System.out.println("expirateTime: "+shiroProperties.getExpirateTime());
	}
	
	@Test
	public void testLogin() throws Exception{
        String uri = "/login";  
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        		.header("authorization", "").param("userCode", "888").param("password", "888")
        ).andReturn();  
        int status = mvcResult.getResponse().getStatus();  
        String content = mvcResult.getResponse().getContentAsString();  
        
        mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
        		.header("authorization", "").param("userCode", "888").param("password", "888")
        ).andReturn();  
  
        System.out.println("content:"+content);
        assertEquals(200, status);  
        //assertEquals(expectedResult, content);
	}
}
