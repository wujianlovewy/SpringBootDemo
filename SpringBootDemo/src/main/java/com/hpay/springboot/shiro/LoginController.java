package com.hpay.springboot.shiro;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController  
public class LoginController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private TokenManager tokenManager;
	
	public static void main(String[] args) {
		SpringApplication.run(LoginController.class, args);
	}
	
    @RequestMapping(value = "/login",method = RequestMethod.POST)  
    public StatelessToken login(String userCode, String password) {  
        System.out.println("userCode:"+userCode);  
        AuthUser user = authService.getUserByUserCode(userCode);  
        if (user == null) {  
            return new StatelessToken(userCode, "valid user");  
        }  
        if(!password.equals(user.getPwd())){  
            return new StatelessToken(userCode, "valid user password");  
        }  
        //成功穿件token返回给客户端保存  
        StatelessToken createToken = tokenManager.createToken(userCode);  
        return createToken;  
    }  
	      
    @RequestMapping(value = "/logout",method = RequestMethod.GET)  
    public String logout(HttpServletRequest request) {  
        String authorization = RequestUtil.newInstance().getRequestHeader(request,"authorization");  
        StatelessToken token = tokenManager.getToken(authorization);  
        if(token!= null){  
            tokenManager.deleteToken(token.getUserCode());  
        }  
        SecurityUtils.getSubject().logout();  
        System.out.println("用户登出");  
        return "logout success";  
    }  
}
