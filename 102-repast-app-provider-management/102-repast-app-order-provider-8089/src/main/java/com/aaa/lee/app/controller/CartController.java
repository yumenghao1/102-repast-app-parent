package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 购物车服务
 */
@RestController

public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    /**
     * 加入购物车方法
     *
     * @param cartItem
     * @param token
     * @return
     */
    @PostMapping("/addProductToCart")
    public ResultData addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem, @RequestParam("stock") Long stock) {
        boolean b = cartService.addProductToCart(cartItem, stock);
        if (b) {
            return super.success(StatusEnum.SUCCESS.getMsg());
        }
        return super.failed(StatusEnum.FAILED.getMsg());
    }

    @PostMapping("/reduceProductToCart")
    public ResultData reduceProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
        ResultData resultData = cartService.reduceProductToCart(cartItem);
        if (resultData.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            return super.success(resultData.getData(), StatusEnum.SUCCESS.getMsg());
        } else {
            return super.failed(StatusEnum.FAILED.getMsg());

        }
    }


}
