package com.aaa.lee.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 13:55
 * @Description
 *      !!!一定要导入注解jar包的时候一定要导tk.myabtis这个包
 *
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@MapperScan("com.aaa.lee.app.mapper")
@EnableFeignClients(basePackages = {"com.aaa.lee.app"})
public class ApplicationRun8089 {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun8089.class, args);
    }

}
