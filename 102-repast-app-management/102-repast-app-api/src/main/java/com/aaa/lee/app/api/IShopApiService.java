package com.aaa.lee.app.api;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.fallback.ShopFallback;
import com.aaa.lee.app.model.CartItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
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
@FeignClient(value = "shop-interface-provider", fallbackFactory = ShopFallback.class)
public interface IShopApiService {

    /**
     * @param
     * @return
     * @throws
     * @author Seven Lee
     * @description 获取店铺熔断数据
     * @date 2019/12/19
     **/
    @GetMapping("/getShopByShopType")
    ResultData getShopByShopType(@RequestParam("token") String token, @RequestParam("shopType") String shopType);

    @GetMapping("/getProductStockById")
    Long getProductStockById();

    @PostMapping("/updateProductStock")
    ResultData updateProductStock(@RequestParam("productId") Long productId, @RequestParam("stock") Integer stock);

    @PostMapping("/updateProductStock")
    ResultData updateProductStock(@RequestBody List t);
}
