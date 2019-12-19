package com.aaa.lee.app.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:17
 * @Description
 **/
//@Component
//@PropertySource("classpath:config/redis.properties")
//@ConfigurationProperties(prefix = "spring.redis")
//@Data
public class RedisProperties {

    private String nodes;
    private String maxAttempts;
    private String commandTimeout;

}
