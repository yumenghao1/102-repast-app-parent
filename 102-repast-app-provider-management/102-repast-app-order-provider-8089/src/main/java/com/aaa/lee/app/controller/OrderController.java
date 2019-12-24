package com.aaa.lee.app.controller;

import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.MemberReceiveAddress;
import com.aaa.lee.app.service.CartService;
import com.aaa.lee.app.service.OrderService;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.vo.TakeOutVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private IRepastService repastServiceFegin;
    @Autowired
    private IShopApiService shopServiceFegin;

//    @Transactional(rollbackFor = Exception.class)
//    @PostMapping("/getTakeOutList")
//    public ResultData getTakeOutList(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
//        try {
//            TakeOutVo takeOutVo1 = orderService.getTakeOutList(token, cartItem, cartService, repastServiceFegin, shopServiceFegin);
//            if (null != takeOutVo1) {
//                return takeOutVo1;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//        ResultData<TakeOutVo> takeOutVoResultData = new ResultData<>();
//        TakeOutVo takeOutVo = new TakeOutVo();
//        try {
//            // 调用 个人优惠券
//            if (null != cartItem) {
//                ResultData<List<Coupon>> couponResultData = repastServiceFegin.getCouponByMemberId(token, cartItem.getMemberId());
//                if (null != couponResultData && couponResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//                    if (null != couponResultData.getData()) {
//                        takeOutVo.setCouponList(couponResultData.getData());
//                    }
//                } else {
//                    throw new NullPointerException("空指针异常1");
//                }
//                ResultData<List<MemberReceiveAddress>> adrResultData = repastServiceFegin.getAdrByMemberId(token, cartItem.getMemberId());
//                if (null != adrResultData && adrResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
//                    if (null != adrResultData.getData()) {
//                        takeOutVo.setMemberReceiveAddressList(adrResultData.getData());
//                    }
//                } else {
//                    throw new NullPointerException("空指针异常2");
//                }
//                // 拉去个人的购物车商品
//                List<CartItem> cartItemList = cartService.getCartItemList(cartItem);
//                if (null != cartItemList) {
//                    // 清空购物车
//                    ResultData<List<CartItem>> listResultData = cartService.cleanProductToCart(cartItemList, 2);
//                    if (listResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode()) && null != listResultData.getData()) {
//                        // 修改购物车的未锁定商品的库存
//                        ResultData resultData = shopServiceFegin.updateProductStock(listResultData.getData());
//                        if (null != resultData && resultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode()) && (Boolean) resultData.getData()) {
//                            return takeOutVoResultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode()).setMsg(StatusEnum.SUCCESS.getMsg()).setData(takeOutVo.setCartItemList(cartItemList));
//                        } else {
//                            throw new NullPointerException("修改库存出现异常需要回滚");
//                        }
//                    } else {
//                        throw new NullPointerException("清空购物车出现异常需要回滚");
//                    }
//                } else {
//                    // 购物车内无商品，不然下单
//                    return takeOutVoResultData.setCode(LoginStatus.LOGIN_FAILED.getCode()).setMsg(StatusEnum.NOT_EXIST.getMsg());
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
//        return null;
//    }

    /**
     * 获取价格和优惠券计算价格
     *
     * @param token
     * @param price
     * @param coupon
     * @return
     */
    @PostMapping("/getProductAndCoupon")
    public Integer getProductAndCoupon(@RequestParam("token") String token, @RequestParam("price") Integer price, @RequestBody Coupon coupon) {
        return orderService.getProductAndCoupon(price, coupon);
    }

    @PostMapping("/getTakeOutList")
    public TakeOutVo getTakeOutList(@RequestParam("token") String token, @RequestBody CartItem cartItem) {
        try {
            TakeOutVo takeOutVo = orderService.getTakeOutList(token, cartItem, cartService, repastServiceFegin, shopServiceFegin);
            if (null != takeOutVo) {
                return takeOutVo;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }
}
