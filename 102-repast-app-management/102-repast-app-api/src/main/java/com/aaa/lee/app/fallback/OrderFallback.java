package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.ShopInformation;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

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
            public ResultData<CartItem> addProductToCart(CartItem cartItem, String token) {
                return new ResultData<CartItem>().setMsg("调用订单熔断成功");
            }
        };
    }
}
