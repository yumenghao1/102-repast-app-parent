package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车服务
 */
@RestController

public class CartController extends BaseController {

    @Autowired
    private CartService cartService;

    /**
     * 购物车的增加
     *
     * @param cartItem
     * @param token
     * @return
     */
    @PostMapping("/addProductToCart")
    public ResultData<CartItem> addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem, @RequestParam("stock") Long stock) {
        ResultData<CartItem> resultData = cartService.addProductToCart(cartItem, stock);
        System.out.println(resultData.toString());
        return resultData;
    }

    /**
     * 购物车的减少
     *
     * @param token
     * @param cartItem
     * @return
     */
    @PostMapping("/reduceProductToCart")
    public ResultData reduceProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
        ResultData resultData = cartService.reduceProductToCart(cartItem);
        if (resultData.getCode().equals(StatusEnum.SUCCESS.getCode())) {
            return super.success(resultData.getData(), StatusEnum.SUCCESS.getMsg());
        } else {
            return super.failed(StatusEnum.FAILED.getMsg());
        }
    }

    /**
     * 清空商品
     *
     * @param token
     * @param cartItems
     * @return
     */
    @PostMapping("/cleanProductToCart")
    public ResultData<List<CartItem>> cleanProductToCart(@RequestParam("token") String token, @RequestBody List<CartItem> cartItems) {
        return cartService.cleanProductToCart(cartItems);
    }

    /**
     * 清空商品
     *
     * @param
     * @param
     * @return
     */
    // TODO 该方法没用
    @PostMapping("/test")
    public List<CartItem> test(@RequestParam("token") String token) {
        return cartService.noPass(Integer.valueOf(token));
    }

    /**
     * 查询购物车列表
     *
     * @param token
     * @param cartItem
     * @return
     */
    @PostMapping("/getCartItemList")
    public ResultData<List<CartItem>> getCartItemList(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
        List<CartItem> cartItemList = cartService.getCartItemList(cartItem);
        if (cartItemList.size() > 0) {
            return super.success(cartItemList, StatusEnum.SUCCESS.getMsg());
        } else {
            return super.failed(StatusEnum.FAILED.getMsg());
        }
    }

}
