package com.aaa.lee.app.config;

import com.aaa.lee.app.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:19
 * @Description
 *      只能使用@Configuration不能使用@SpringBootApplication注解
 **/
//@Configuration
public class RedisConfig {

   /* @Autowired
    private RedisProperties redisProperties;

    public JedisCluster jedisCluster() {
        String nodes = redisProperties.getNodes();
        String[] split = nodes.split(",");
        Set<HostAndPort> hostAndPortSet = new HashSet<HostAndPort>();
        for (String hostPort : split) {
            String[] split1 = hostPort.split(":");
            HostAndPort hostAndPort = new HostAndPort(split1[0], Integer.parseInt(split1[1]));
            hostAndPortSet.add(hostAndPort);
        }
        JedisCluster jedisCluster = new JedisCluster(hostAndPortSet, Integer.parseInt(redisProperties.getCommandTimeout()), Integer.parseInt(redisProperties.getMaxAttempts()));
        return jedisCluster;
    }*/

}
