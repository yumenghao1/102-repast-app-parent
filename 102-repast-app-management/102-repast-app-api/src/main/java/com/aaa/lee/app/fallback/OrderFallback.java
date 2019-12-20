package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
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
            public ResultData addProductToCart(String token, CartItem cartItem, Long stock) {
                return new ResultData().setMsg("调用订单熔断成功");
            }
        };
    }
}
