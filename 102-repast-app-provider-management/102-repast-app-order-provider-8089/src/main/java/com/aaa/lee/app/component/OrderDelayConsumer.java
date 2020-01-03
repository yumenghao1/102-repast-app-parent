package com.aaa.lee.app.component;

import com.aaa.lee.app.api.IOrderApiService;
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
@RabbitListener(queues = "process_queue")
public class OrderDelayConsumer {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private IShopApiService shopApiService;

    @Transactional(rollbackFor = Exception.class)
    @RabbitHandler
    public void orderConsumer(MessageVo msg) throws Exception {
        try {
            // 获取消息队列中消息拿出订单SN
            String orderSn = msg.getOrderSn();
            // 根据sn查询该订单
            OrderVo orderByOrderSn = orderService.getOrderByOrderSn(orderSn, orderItemService);
            // 该订单的状态如果为待付款则取消订单，因为超过规定时间
            Order order = orderByOrderSn.getOrder();
            if (order.getStatus().equals(StaticProperties.NO_PAY)) {
                // 修改订单状态
                order.setStatus(StaticProperties.INVALID);
                Integer update = orderService.update(order);
                if (update > 0) {
                    ResultData resultData = shopApiService.updateProductStock(orderByOrderSn.getOrderItemList());
                    if (resultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())){}else {
                        throw new Exception("库存出现异常");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("消息队列出现异常");
        }

    }
}
