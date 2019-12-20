package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.ShopInformation;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车order接口
 */
@RestController
@Api(value = "购物车", tags = "购物车服务接口")
public class CartController extends BaseController {

    @Autowired
    private IOrderApiService iOrderApiService;
    @Autowired
    private IShopApiService iShopApiService;

    /**
     * @param token
     * @param cartItem 商品id 店铺id 会员id
     * @return
     */
    @PostMapping("/addProductToCart")
    @ApiOperation(value = "添加购物车", notes = "添加购物车并保存到购物车表，并对库存数量进行操作")
    public ResultData addProductToCart(String token, CartItem cartItem) {
        ResultData cartItemResultData = iOrderApiService.addProductToCart(token, cartItem, iShopApiService.getProductStockById());
        if (cartItemResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            ResultData resultData = iShopApiService.updateProductStock(cartItem.getProductId());
            return resultData;
        }
        return super.failed(StatusEnum.FAILED.getMsg());
    }


}
