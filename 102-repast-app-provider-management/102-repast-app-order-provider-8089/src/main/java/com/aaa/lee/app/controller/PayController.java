package com.aaa.lee.app.controller;

import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class PayController {
    @Autowired
    private PayService payService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 进行微信支付
     * @param token
     * @param openid
     * @param orderSn
     * @param amount
     * @return
     */
    @PostMapping("/wxPay")
    public Map<String, Object> wxPay(@RequestParam("token") String token, @RequestParam("openid") String openid, @RequestParam("orderSn") String orderSn, @RequestParam("amount") Float amount) {
        return payService.wxPay(openid, orderSn, amount, request);
    }

}
