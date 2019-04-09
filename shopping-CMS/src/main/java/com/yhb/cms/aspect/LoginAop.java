package com.yhb.cms.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/22 17:37
 * @Description:
 */
/*@Component
@Aspect*/
public class LoginAop {

    @Pointcut("execution(public * com.yhb.cms.controller.*.*(..))")
    public void loginAop(){
    }

    @Before("loginAop()")
    public void before(JoinPoint joinPoint){
    }
}
