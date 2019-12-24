package com.aaa.lee.app.service;

import com.aaa.lee.app.Myconst.WXConst;
import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.MemberReceiveAddress;
import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.PayUtil;
import com.aaa.lee.app.vo.TakeOutVo;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: YMH
 * @CreateDate: 2019/12/24 23:37
 * @Version:
 */
@Service
public class OrderService {

    private static Exception GetTakeOutException = new Exception(StaticProperties.GETTAKEOUTEXCEPTION);

    /**
     * 获取总价格和优惠券进行计算价格
     *
     * @param
     * @param price
     * @param coupon
     * @return
     */
    public Integer getProductAndCoupon(Integer price, Coupon coupon) {
        if (price < Integer.valueOf(coupon.getMinPoint().toString())) {
            return null;
        } else {
            price = price - Integer.valueOf(coupon.getAmount().toString());
            return price;
        }
    }


    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description
     * @date create in 2019/12/24 23:39
     **/
    @Transactional(rollbackFor = Exception.class)
    public TakeOutVo getTakeOutList(String token, CartItem cartItem, CartService cartService, IRepastService repastServiceFegin, IShopApiService shopServiceFegin) throws Exception {
        TakeOutVo takeOutVo = new TakeOutVo();
        // 数据为空直接返回
        if (null == cartItem) {
            return null;
        }
        // 从购物车实体类获取用户id
        Long memberId = cartItem.getMemberId();
        // 根据用户id查询有用的优惠券
        ResultData<List<Coupon>> couponResultData = repastServiceFegin.getCouponByMemberId(token, memberId);
        // 判断优惠券是否调用成功,调用失败也可以进行下单
        if (null != couponResultData && couponResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            // 如果调用服务成功，无优惠券数据不放入vo
            if (null != couponResultData.getData()) {
                takeOutVo.setCouponList(couponResultData.getData());
            }
        }
        // 根据用户id查询个人的收货地址列表,即便调用失败也不影响下单
        ResultData<List<MemberReceiveAddress>> adrResultData = repastServiceFegin.getAdrByMemberId(token, memberId);
        if (null != adrResultData && adrResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            if (null != adrResultData.getData()) {
                takeOutVo.setMemberReceiveAddressList(adrResultData.getData());
            }
        } else {
            throw GetTakeOutException;
        }
        // 根据用户id,店铺id,拉去个人的还存在的购物车商品
        List<CartItem> cartItemList = cartService.getCartItemList(cartItem);
        if (null != cartItemList) {
            // 清空购物车
            ResultData<List<CartItem>> listResultData = cartService.cleanProductToCart(cartItemList, 2);
            if (listResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode()) && null != listResultData.getData()) {
                // 修改购物车的未锁定商品的库存
                ResultData resultData = shopServiceFegin.updateProductStock(listResultData.getData());
                if (null != resultData && resultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode()) && (Boolean) resultData.getData()) {
                    return takeOutVo.setCartItemList(cartItemList);
                } else {
                    throw GetTakeOutException;
                }
            } else {
                throw GetTakeOutException;
            }
        }
        return null;
    }
}
