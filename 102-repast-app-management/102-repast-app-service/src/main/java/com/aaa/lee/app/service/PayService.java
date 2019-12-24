package com.aaa.lee.app.service;

import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.utils.PayUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Service
public class PayService {

    public Map<String, Object> wxPay(String openId, String orderSn, Float amount, HttpServletRequest request) {
        Map<String, Object> jsonObject = PayUtil.wxPay(orderSn, openId, amount, request);
        if (StaticProperties.OK.equals(jsonObject.get(StaticProperties.PAY_MAP_KEY))) {
            return jsonObject;
        }
        return null;
    }
}
