package com.aaa.lee.app.component;

import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.service.OrderItemService;
import com.aaa.lee.app.service.OrderService;
import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.vo.MessageVo;
import com.aaa.lee.app.vo.OrderVo;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RabbitListener(queues = "affirm_process_queue")
public class OrderAffirmConsumer {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private IShopApiService shopApiService;

    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    public void orderConsumer(MessageVo msg) throws Exception {
        System.out.println("该订单已经到自动收货时间，时间为7天");
        try {
            // 获取消息队列中消息拿出订单SN
            String orderSn = msg.getOrderSn();
            // 根据sn查询该订单
            OrderVo orderByOrderSn = orderService.getOrderByOrderSn(orderSn, orderItemService);
            // 该订单的状态是否为退货成功的订单
            Order order = orderByOrderSn.getOrder();
            if (order.getStatus().equals(StaticProperties.INVALID)) {
                return;
            }
            order.setStatus(StaticProperties.INVALID);
            orderService.update(order);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("消息队列出现异常");
        }
    }
}
