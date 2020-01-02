package com.aaa.lee.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2020/1/2 14:15
 * @Description
 *      自定义注解
 *      @Target():标识了自定义所需要使用的范围(所需要使用的地方)
 *      @Retention():标识了自定义生效的时间
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TokenAnnocation {

    /**
     * @author Seven Lee
     * @description
     *      要执行的操作类型：eg:delete操作，login操作
     * @param []
     * @date 2020/1/2
     * @return java.lang.String
     * @throws
    **/
    String operationType() default "";

    /**
     * @author Seven Lee
     * @description
     *      要执行的具体操作：eg：删除用户，添加用户
     * @param []
     * @date 2020/1/2
     * @return java.lang.String
     * @throws 
    **/
    String operationName() default "";

}
