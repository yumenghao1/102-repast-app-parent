package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
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
public class ShopFallback implements FallbackFactory<IShopApiService> {

    @Override
    public IShopApiService create(Throwable throwable) {
        IShopApiService iShopApiService = new IShopApiService() {
            @Override
            public ResultData<ShopInformation> getShopByShopType(String token, String shopType) {

                return new ResultData<ShopInformation>().setMsg("调用店铺熔断成功");
            }

            @Override
            public Long getProductStockById() {
                System.out.println("调用获取库存熔断成功");
                return 10L;
            }

            @Override
            public ResultData updateProductStock(Long productId) {
                return new ResultData().setMsg("减少库存成功熔断成功");
            }
        };
        return iShopApiService;
    }
}
