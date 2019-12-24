package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IOrderApiService;
import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseController;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.MemberReceiveAddress;
import com.aaa.lee.app.model.OrderItem;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
//        ResultData<TakeOutVo> resultData = new ResultData<TakeOutVo>();
//        // vo类，里面放购物车商品，优惠券，收货地址
//        TakeOutVo takeOutVo = new TakeOutVo();
//        // 获取个人收货地址
//        ResultData<List<MemberReceiveAddress>> adressResultData = repastService.getAdrByMemberId(token, cartItem.getMemberId());
//        if (adressResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//            takeOutVo.setMemberReceiveAddressList(adressResultData.getData());
//        }
//        // 获取个人未失效优惠券
//        ResultData<List<Coupon>> couponResultData = repastService.getCouponByMemberId(token, cartItem.getMemberId());
//        if (couponResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//            takeOutVo.setCouponList(couponResultData.getData());
//        }
//        // 获取购物车列表，
//////        ResultData<List<CartItem>> cartResultData = iOrderApiService.getCartItemList(token, cartItem);
//////        if (cartResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//////            takeOutVo.setCartItemList(cartResultData.getData());
//////        } else {
//////            return resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode()).setMsg(StatusEnum.NOT_EXIST.getMsg());
//////        }
//        // 查询购物车未减库存商品，进行库存减少
//        ResultData<List<CartItem>> listResultData = iOrderApiService.cleanProductToCart(token, cartResultData.getData(), Integer.valueOf(StatusEnum.FAILED.getCode()));
//        // 判断是否库存减少成功
//        if (null != listResultData && LoginStatus.LOGIN_SUCCESS.getCode().equals(listResultData.getCode())) {
//            if (null != listResultData.getData()) {
//                if (!shopApiService.updateProductStock(listResultData.getData()).getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//                    // 失败异常异常
//                }
//            }
//            return resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode()).setMsg(StatusEnum.SUCCESS.getMsg()).setData(takeOutVo);
//        }
//
//        return resultData.setCode(LoginStatus.LOGIN_FAILED.getCode()).setMsg(StatusEnum.FAILED.getMsg());
//    }
        return iOrderApiService.getTakeOutList(token,cartItem);
    }
}
