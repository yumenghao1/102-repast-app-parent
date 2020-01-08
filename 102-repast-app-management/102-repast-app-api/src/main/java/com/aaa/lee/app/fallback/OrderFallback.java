package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:57
 * @Description
 **/
@Component
public class OrderFallback implements FallbackFactory<IOrderApiService> {


    @Override
    public IOrderApiService create(Throwable throwable) {
        return new IOrderApiService() {
            @Override
            public boolean addProductToCart(String token, CartItem cartItem) {
                System.out.println("添加购物车商品报错熔断成功");
                return false;
            }

            @Override
            public boolean reduceProductToCart(String token, CartItem cartItem) {
                System.out.println("减少购物车商品报错熔断成功");
                return false;
            }

            @Override
            public ResultData cleanProductToCart(String token, List<CartItem> cartItems, Integer status) {
                return new ResultData().setMsg("清空购物车商品报错熔断成功");
            }

            @Override
            public ResultData getCartItemList(String token, CartItem cartItem) {
                return new ResultData().setMsg("获取购物车列表报错熔断成功");
            }

            @Override
            public TakeOutVo getOrderList(String token, Integer orderType,CartItem cartItem) {
                List<CartItem> cartItems = new ArrayList<>();
                cartItems.add(new CartItem().setProductName("测试熔断失败"));
                return new TakeOutVo().setCartItemList(cartItems);
            }

            @Override
            public Integer getProductAndCoupon(String token, Integer price, Coupon coupon) {
                System.out.println("计算订单列表报错熔断成功");
                return null;
            }

            @Override
            public Map<String, Object> wxPay(String token, String openid, String orderId, Float amount) {
                return new HashMap<>();
            }

            @Override
            public boolean insertOrder(String token, OrderVo orderVo) {
                return false;
            }

            @Override
            public List<Order> getOrderListByMemberId(String token, Long memberId, Integer status) {
                return null;
            }

            @Override
            public OrderVo getOrderVoByOrderSn(String token, String orderSn) {
                return null;
            }

            @Override
            public Integer addReturnApply(String token, String orderSn, String reason, MultipartFile[] files) {
                return null;
            }

            @Override
            public boolean affirmOrder(String token, String orderSn) {
                return false;
            }


            @Override
            public boolean test(String token) {
                return false;
            }
        };
    }
}
