package com.aaa.lee.app.service;

import com.aaa.lee.app.config.RabbitConfig;
import com.aaa.lee.app.vo.MessageVo;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelaySendMsgService {
    @Autowired
    private AmqpTemplate rabbitTemplate;

   /**
    * @author YMH
    * @description
           最后将待付款的订单消息发送到队列
    * @param
    * @date create in 2020/1/4 2:03
    * @return 
    * @throws 
    **/ 
    public void sendDelayMsg(MessageVo msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE, RabbitConfig.DELAY_KEY, msg, message -> {
            message.getMessageProperties().setExpiration(msg.getMillis());
            return message;
        });
    }
    /**
     * @author YMH
     * @description
    最后将待付款的订单消息发送到队列
     * @param
     * @date create in 2020/1/4 2:03
     * @return
     * @throws
     **/
    public void sendAffirmMsg(MessageVo msg) {
        rabbitTemplate.convertAndSend(RabbitConfig.AFFIRM_EXCHANGE, RabbitConfig.AFFIRM_KEY, msg, message -> {
            message.getMessageProperties().setExpiration(msg.getMillis());
            return message;
        });
    }
}
