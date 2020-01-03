package com.aaa.lee.app.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:config/rabbitmq.properties")
@ConfigurationProperties(prefix = "spring.rabbit")
@Data
@NoArgsConstructor
public class RabbitMQProperties {
    private String host ="192.168.130.128";
    private int port=5672;
    private String vHost="YMH1";
    private String username="admin";
    private String password="admin";
}
