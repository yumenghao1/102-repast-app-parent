package com.aaa.lee.app.component;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@EnableScheduling
public class CartTimeoutCancelTask {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CartService cartService;

    @Scheduled(cron = "0 */10 * * * *")
    private void CartTimeoutCancelTask() {
        System.out.println("定时清理");
        List<CartItem> cartItems = cartService.noPass(Integer.valueOf(StatusEnum.SUCCESS.getCode()));
        if (cartItems.size() > 0) {
            System.out.println("回复这些商品库存"+cartItems.toString());
        }
    }

}
