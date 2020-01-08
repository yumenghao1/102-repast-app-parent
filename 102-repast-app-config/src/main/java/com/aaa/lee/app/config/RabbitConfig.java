package com.aaa.lee.app.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    // 定义路由器键，指定路由规则
    public static final String DELAY_KEY = "delay";
    //直接交换器，绑定了延迟消息队列
    public static final String DELAY_EXCHANGE = "delay_exchange";
    //该队列没有直接消费者，该队列的消息到达一定时间后自动过期，将信息转发给正常队列进行处理
    public static final String DELAY_QUEUE = "delay_queue";
    //直接交换器，绑定了正常消息队列
    public static final String PROCESS_EXCHANGE = "process_exchange";
    //该消息队列为正常消息队列，接受过期的消息进行处理
    public static final String PROCESS_QUEUE = "process_queue";

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 延迟队列交换机
     * @date create in 2020/1/4 0:53
     **/
    @Bean
    public Exchange delayExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 延迟队列，绑定消息过期转发交换器
     * @date create in 2020/1/4 0:53
     **/
    @Bean
    public Queue delayQueue() {
        return QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", PROCESS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DELAY_KEY)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 路由键绑定延迟队列和交换机
     * @date create in 2020/1/4 1:06
     **/
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_KEY).noargs();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 直接交换器
     * @date create in 2020/1/4 1:08
     **/
    @Bean
    public Exchange processExchange() {
        return ExchangeBuilder
                .directExchange(PROCESS_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 正常队列
     * @date create in 2020/1/4 1:09
     **/
    @Bean
    public Queue processQueue() {
        return QueueBuilder.durable(PROCESS_QUEUE)
                .build();
    }

    /**
     * @author YMH
     * @description
            路由键绑定交换机和队列
     * @param 
     * @date create in 2020/1/4 1:15
     * @return 
     * @throws 
     **/
    @Bean
    public Binding processBinding() {
        return BindingBuilder.bind(processQueue()).to(processExchange()).with(DELAY_KEY).noargs();
    }

  //----------------------------------------------------------------------------------------------
    //七天自动收货
    // 定义路由器键，指定路由规则
    public static final String AFFIRM_KEY = "affirm";
    //直接交换器，绑定了延迟消息队列
    public static final String AFFIRM_EXCHANGE="affirm_exchange";
    //该队列没有直接消费者，该队列的消息到达一定时间后自动过期，将信息转发给正常队列进行处理
    public static final String AFFIRM_QUEUE = "affirm_queue";
    //直接交换器，绑定了正常消息队列
    public static final String AFFIRM_PROCESS_EXCHANGE = "affirm_process_exchange";
    //该消息队列为正常消息队列，接受过期的消息进行处理
    public static final String AFFIRM_PROCESS_QUEUE = "affirm_process_queue";

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 延迟队列交换机
     * @date create in 2020/1/4 0:53
     **/
    @Bean
    public Exchange affirmExchange() {
        return ExchangeBuilder
                .directExchange(AFFIRM_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 延迟队列，绑定消息过期转发交换器
     * @date create in 2020/1/4 0:53
     **/
    @Bean
    public Queue affirmQueue() {
        return QueueBuilder.durable(AFFIRM_QUEUE)
                .withArgument("x-dead-letter-exchange", AFFIRM_PROCESS_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", AFFIRM_KEY)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 路由键绑定延迟队列和交换机
     * @date create in 2020/1/4 1:06
     **/
    @Bean
    public Binding affirmBinding() {
        return BindingBuilder.bind(affirmQueue()).to(affirmExchange()).with(AFFIRM_KEY).noargs();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 直接交换器
     * @date create in 2020/1/4 1:08
     **/
    @Bean
    public Exchange affirmProcessExchange() {
        return ExchangeBuilder
                .directExchange(AFFIRM_PROCESS_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 正常队列
     * @date create in 2020/1/4 1:09
     **/
    @Bean
    public Queue affirmProcessQueue() {
        return QueueBuilder.durable(AFFIRM_PROCESS_QUEUE)
                .build();
    }

    /**
     * @author YMH
     * @description
    路由键绑定交换机和队列
     * @param
     * @date create in 2020/1/4 1:15
     * @return
     * @throws
     **/
    @Bean
    public Binding affirmProcessBinding() {
        return BindingBuilder.bind(affirmProcessQueue()).to(affirmProcessExchange()).with(AFFIRM_KEY).noargs();
    }
}
