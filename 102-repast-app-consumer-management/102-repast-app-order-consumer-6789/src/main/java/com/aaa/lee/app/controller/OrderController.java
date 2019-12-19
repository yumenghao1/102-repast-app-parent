package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.ShopInformation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @Autowired
    private IShopApiService shopApiService;

    @GetMapping("/getShopByShopType")
    @ApiOperation(value = "获取店铺", notes = "根据消费类型选择店铺")
    public ResultData<ShopInformation> getShopByShopType(@RequestParam("shopType") String shopType) {
        return shopApiService.getShopByShopType(shopType);
    }
}
