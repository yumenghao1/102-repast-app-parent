package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.ShopInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单order接口
 */
@RestController
@Api(value = "订单", tags = "订单服务接口")
public class OrderController extends BaseController {
    /**
     * 根据根据消费类型选择店铺
     */
    @Autowired
    private IShopApiService shopApiService;
    // TODO 该方法是测试方法需要调用shop接口
    @GetMapping("/getShopByShopType")
    @ApiOperation(value = "获取店铺", notes = "根据消费类型选择店铺")
    public ResultData getShopByShopType(@RequestParam("shopType") String shopType,String token) {
        return shopApiService.getShopByShopType(shopType,token);
    }

}
