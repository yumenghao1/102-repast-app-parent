package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.MemberReceiveAddress;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.service.OrderItemService;
import com.aaa.lee.app.service.OrderOperateHistoryService;
import com.aaa.lee.app.service.OrderService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private IRepastService repastServiceFegin;
    @Autowired
    private IShopApiService shopServiceFegin;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderOperateHistoryService orderOperateHistoryService;

    /**
     * 获取价格和优惠券计算价格
     *
     * @param token
     * @param price
     * @param coupon
     * @return
     */
    @PostMapping("/getProductAndCoupon")
    public Integer getProductAndCoupon(@RequestParam("token") String token, @RequestParam("price") Integer price, @RequestBody Coupon coupon) {
        return orderService.getProductAndCoupon(price, coupon);
    }

    @PostMapping("/getTakeOutList")
    public TakeOutVo getTakeOutList(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
        try {
            TakeOutVo takeOutVo = orderService.getTakeOutList(token, cartItem, cartService, repastServiceFegin, shopServiceFegin);
            if (null != takeOutVo) {
                return takeOutVo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @PostMapping("/insertOrder")
    public boolean insertOrder(@RequestParam("token") String token, @RequestBody OrderVo orderVo) {
        try {
            return orderService.insertOrder(orderVo, orderItemService, orderOperateHistoryService);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
