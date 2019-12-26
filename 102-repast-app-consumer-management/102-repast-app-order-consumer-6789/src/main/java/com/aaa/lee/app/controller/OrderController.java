package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单order接口
 */
@RestController
@Api(value = "订单", tags = "订单服务接口")
public class OrderController extends BaseController {
    @Autowired
    private IOrderApiService iOrderApiService;
    /**
     * 根据根据消费类型选择店铺
     */
    @Autowired
    private IShopApiService shopApiService;
    @Autowired
    private IRepastService repastService;

    // TODO 该方法是测试方法需要调用shop接口
    @GetMapping("/getShopByShopType")
    @ApiOperation(value = "获取店铺", notes = "根据消费类型选择店铺")
    public ResultData getShopByShopType(String token, String shopType) {
        return shopApiService.getShopByShopType(token, shopType);
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description
     * @date create in 2019/12/25 0:32
     **/
    @PostMapping("/getOrderList")
    @ApiOperation(value = "获取下单参数列表", notes = "获取下单信息进行下单")
    public ResultData getOrderList(String token, CartItem cartItem, Integer orderType) {
        TakeOutVo takeOutList = iOrderApiService.getOrderList(token, orderType, cartItem);
        if (null != takeOutList) {
            return success(takeOutList);
        }
        return failed();
    }

    /**
     * 获取订单钱数和优惠券进行减免价格
     *
     * @param token
     * @param
     * @return
     */
    @PostMapping("/getProductAndCoupon")
    @ApiOperation(value = "获取钱和优惠券计算金额", notes = "获取钱和优惠券计算金额")
    public ResultData getProductAndCoupon(String token, Integer price, Coupon coupon) {
        price = iOrderApiService.getProductAndCoupon(token, price, coupon);
        if (null != price) {
            return success(price);
        }
        return failed();
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 根据购物车商品进行添加到订单表中
     * @date create in 2019/12/25 15:32
     **/
    @PostMapping("/insertOrder")
    @ApiOperation(value = "进行下单", notes = "添加购物车商品到订单表中")
    public ResultData insertOrder(String token, @RequestBody OrderVo orderVo) {
        boolean b = iOrderApiService.insertOrder(token, orderVo);
        if (b) {
            return success();
        } else {
            return failed();
        }

    }

}
