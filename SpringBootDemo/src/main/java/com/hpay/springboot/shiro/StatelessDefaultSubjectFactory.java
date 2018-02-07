package com.hpay.springboot.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * 无状态主题工厂
 * @author jwu
 *
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

	@Override  
    public Subject createSubject(SubjectContext context) {  
        //不创建session    
        context.setSessionCreationEnabled(false);  
        return super.createSubject(context);  
    }  
	
}
