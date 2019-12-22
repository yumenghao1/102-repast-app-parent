package com.aaa.lee.app.api;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.fallback.OrderFallback;
import com.aaa.lee.app.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    ResultData<CartItem> addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem, @RequestParam("stock") Long stock);

    @PostMapping("/reduceProductToCart")
    ResultData reduceProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    @PostMapping("/cleanProductToCart")
    ResultData<List<CartItem>> cleanProductToCart(@RequestParam("token") String token, @RequestBody List<CartItem> cartItems);

    @PostMapping("/test")
    List<CartItem> test(@RequestParam("token") String token);

    @PostMapping("/getCartItemList")
    ResultData<List<CartItem>> getCartItemList(@RequestParam("token") String token, @RequestBody CartItem cartItem);
}
