package com.aaa.lee.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:23
 * @Description
 **/
@SpringBootApplication
@EnableEurekaServer
public class ApplicationRun7082 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun7082.class, args);
    }

}
