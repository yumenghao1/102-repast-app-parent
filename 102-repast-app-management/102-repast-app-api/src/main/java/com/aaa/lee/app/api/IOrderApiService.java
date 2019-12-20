package com.aaa.lee.app.api;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.fallback.OrderFallback;
import com.aaa.lee.app.fallback.ShopFallback;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.ShopInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    ResultData<CartItem> addProductToCart(@RequestBody CartItem cartItem, @RequestParam("token") String token);
}
