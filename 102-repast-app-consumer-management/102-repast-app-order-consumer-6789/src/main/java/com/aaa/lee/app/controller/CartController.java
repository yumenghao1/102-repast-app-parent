package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        ResultData<CartItem> cartItemResultData = iOrderApiService.addProductToCart(token, cartItem, iShopApiService.getProductStockById());
        if (cartItemResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            return checkProductStock(cartItemResultData.getData());
        }
        return super.failed(StatusEnum.FAILED.getMsg());
    }

    /**
     * @param token
     * @param cartItem 商品id 店铺id 会员id
     * @return
     */
    @PostMapping("/reduceProductToCart")
    @ApiOperation(value = "减少购物车", notes = "修改购物车内商品数量")
    public ResultData reduceProductToCart(String token, CartItem cartItem) {
        ResultData reduceProduct = iOrderApiService.reduceProductToCart(token, cartItem);
        // 为true是对购物车商品减少成功，在根据resultData来判断是否对库存进行操作
        if (reduceProduct.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode()) && (boolean) reduceProduct.getData()) {
            return checkProductStock(cartItem);
        }
        return super.failed(StatusEnum.FAILED.getMsg());
    }

    /**
     * @param token
     * @param
     * @return
     */
    @PostMapping("/cleanProductToCart")
    @ApiOperation(value = "清空购物车", notes = "清空购物车")
    public ResultData cleanProductToCart(String token, @RequestBody List<CartItem> cartItems) {
//        boolean b = false;
        ResultData reduceProduct = iOrderApiService.cleanProductToCart(token, cartItems);
        // 为true是对购物车商品清空成功，在根据resultData来判断是否对库存进行操作，被传回来的商品就是一定要对库存进行操作
        if (reduceProduct.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            return iShopApiService.updateProductStock(cartItems);
        }
        return super.failed(StatusEnum.FAILED.getMsg());
    }

    /**
     * 验证库存是否修改成功
     *
     * @param cartItem
     * @return
     */
    private ResultData checkProductStock(CartItem cartItem) {
        if (iShopApiService.updateProductStock(cartItem.getProductId(), cartItem.getQuantity()).getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            return super.success(StatusEnum.SUCCESS.getMsg());
        } else {
            return super.failed(StatusEnum.FAILED.getMsg());
        }
    }

    /**
     * 获取购物车列表
     *
     * @param token
     * @param cartItem
     * @return
     */
    @PostMapping("/getCartItemList")
    @ApiOperation(value = "获取购物车列表", notes = "获取购物车列表")
    public ResultData getCartItemList(String token, CartItem cartItem) {
        return iOrderApiService.getCartItemList(token, cartItem);
    }


}
