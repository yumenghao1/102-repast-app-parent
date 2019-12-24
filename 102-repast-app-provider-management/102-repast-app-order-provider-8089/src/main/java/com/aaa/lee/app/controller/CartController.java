package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
     * @author YMH
     * @description
            增加购物车
     * @param 
     * @date create in 2019/12/25 1:48
     * @return 
     * @throws 
     **/
    @PostMapping("/addProductToCart")
    public ResultData<CartItem> addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem, @RequestParam("stock") Long stock) {
        return cartService.addProductToCart(cartItem, stock);
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
     * 清空个人购物车商品
     * 分为两种情况，返回两种数据
     * status为1是手动清空购物车 则返回锁定库存商品进行库存回复
     * status为2是下单清空购物车 则返回未锁定库存商品进行库存减少
     *
     * @param token
     * @param cartItems
     * @return
     */
    @PostMapping("/cleanProductToCart")
    public ResultData<List<CartItem>> cleanProductToCart(@RequestParam("token") String token, @RequestBody List<CartItem> cartItems, @RequestParam("status") Integer status) {
        ResultData<List<CartItem>> listResultData = cartService.cleanProductToCart(cartItems, status);
        System.out.println(listResultData.toString());
        return listResultData;
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
    public Boolean test(@RequestParam("token") String token) {
        return false;
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
