package com.aaa.lee.app.api;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.fallback.OrderFallback;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.vo.TakeOutVo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:55
 * @Description 如果是简单类型传递数据可以传递多个，但是每一个必须要用@RequestParam
 * 但是如果是包装类型，只能传递一个，也必须要用@RequestBody
 **/
@FeignClient(value = "order-interface-provider", fallbackFactory = OrderFallback.class)
public interface IOrderApiService {

    @PostMapping("/addProductToCart")
    boolean addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    @PostMapping("/reduceProductToCart")
    boolean reduceProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    @PostMapping("/cleanProductToCart")
    ResultData<List<CartItem>> cleanProductToCart(@RequestParam("token") String token, @RequestBody List<CartItem> cartItems, @RequestParam("status") Integer status);

    @PostMapping("/test")
    boolean test(@RequestParam("token") String token);

    @PostMapping("/getCartItemList")
    ResultData<List<CartItem>> getCartItemList(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    @PostMapping("/getTakeOutList")
    TakeOutVo getTakeOutList(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    @PostMapping("/getProductAndCoupon")
    Integer getProductAndCoupon(@RequestParam("token") String token, @RequestParam("price") Integer price, @RequestBody Coupon coupon);

    @PostMapping("/wxPay")
    Map<String, Object> wxPay(@RequestParam("token") String token, @RequestParam("openid")   String openid, @RequestParam("orderSn") String orderSn, @RequestParam("amount") Float amount);
}
