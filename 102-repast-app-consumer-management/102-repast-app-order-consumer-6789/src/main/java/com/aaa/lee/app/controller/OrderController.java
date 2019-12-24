package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.status.LoginStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * 获取数据进行下单
     *
     * @param token
     * @param cartItem
     * @return
     */
    @PostMapping("/getTakeOutList")
    @ApiOperation(value = "获取外卖参数列表", notes = "获取下单信息进行外卖下单")
    public ResultData getTakeOutList(String token, CartItem cartItem) {
        return iOrderApiService.getTakeOutList(token, cartItem);
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
        ResultData<Coupon> couponResultData = repastService.selectCouponById(token, Integer.valueOf(coupon.getId().toString()));
        if (null != couponResultData) {
            if (couponResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
                return iOrderApiService.getProductAndCoupon(token, price, couponResultData.getData());
            }
        }
        return super.failed();
    }


}
