package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.service.CartService;
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
    public ResultData<CartItem> addProductToCart(@RequestBody CartItem cartItem, @RequestParam("token") String token) {
        boolean status = cartService.addProductToCart(cartItem, token);
        if (status) {
            return success("测试购物车token成功");
        } else {
            return failed("测试购物车token失败");
        }
    }


}
