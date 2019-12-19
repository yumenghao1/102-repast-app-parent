package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.Member;
import com.aaa.lee.app.model.ShopInformation;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:57
 * @Description
 **/
@Component
public class ShopFallback implements FallbackFactory<IShopApiService> {

    @Override
    public IShopApiService create(Throwable throwable) {
        IShopApiService iShopApiService = new IShopApiService() {
            @Override
            public ResultData<ShopInformation> getShopByShopType(String shopType) {
                return null;
            }
        };
        return iShopApiService;
    }
}
