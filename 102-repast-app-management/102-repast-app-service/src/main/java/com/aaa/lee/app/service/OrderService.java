package com.aaa.lee.app.service;

import com.aaa.lee.app.Myconst.WXConst;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.PayUtil;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 订单service
 */
@Service
public class OrderService {

    /**
     * 获取总价格和优惠券进行计算价格
     *
     * @param
     * @param price
     * @param coupon
     * @return
     */
    public ResultData getProductAndCoupon(Integer price, Coupon coupon) {
        ResultData resultData = new ResultData<>();
        if (price < Integer.valueOf(coupon.getMinPoint().toString())) {
            return resultData.setCode(LoginStatus.LOGIN_FAILED.getCode())
                             .setMsg(StatusEnum.FAILED.getMsg());
        } else {
            price = price - Integer.valueOf(coupon.getAmount().toString());
        }
        return resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode())
                         .setMsg(StatusEnum.SUCCESS.getMsg())
                         .setData(price);
    }



}
