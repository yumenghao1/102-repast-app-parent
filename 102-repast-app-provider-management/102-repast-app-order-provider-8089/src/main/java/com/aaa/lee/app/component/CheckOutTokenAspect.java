package com.aaa.lee.app.component;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.utils.StringUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 验证token，判断用户是否登录
 */
@Component
@Aspect
public class CheckOutTokenAspect {

    /**
     * 拦截本类中所有方法
     */
    @Pointcut("execution(public * com.aaa.lee.app.controller.*.*(..))")
    public void token() {
    }

    /**
     * 验证token，为空去登录，否则继续执行
     *
     * @param joinPoint
     * @return
     */
    @Around("token()")
    public Object doToken(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = null;
            if (StringUtil.isNotEmpty((String) joinPoint.getArgs()[1])) {
                proceed = joinPoint.proceed();
                return proceed;
            } else {
                return new ResultData<>().setCode(LoginStatus.USER_HAS_NOT_PERMISSION.getCode()).setMsg(LoginStatus.USER_HAS_NOT_PERMISSION.getMsg());
            }
    }
}

