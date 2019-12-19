package com.aaa.lee.app.controller;

import com.aaa.lee.app.model.Member;
import com.aaa.lee.app.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:00
 * @Description
 **/
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * @author Seven Lee
     * @description
     *      执行登录操作
     *      这个登录操作来源于微信小程序(肯定是使用微信授权登录)
     *      使用Map接收
     *      入参的时候使用Map，出参的也是Map
     *      以后在真实开发环境中，所有的请求全部都是ajax--->既然是异步就会返回到java的controller中是json对象(JSON和Map可以进行直接转换)
     *      dataMap:
     *          open_id:微信的用户唯一标识符(相当于主键id)
     *          nick_name:昵称
     *
     *      需要重写微信的登录(在小程序开发工具中重写某一个登录方法)--->把这个结果重新封装成一个实体类
     *
     * @param []
     * @date 2019/12/19
     * @return java.lang.Boolean
     * @throws 
    **/
    /*@PostMapping("/doLogin")
    public Boolean doLogin(Map<Object, Object> dataMap) {
        dataMap.get("nick_name");
        dataMap.get("open_id");
        dataMap.get("ip");
        dataMap.get("region");
    }*/
    @PostMapping("/doLogin")
    public Boolean doLogin(@RequestBody Member member) {
        // 调用service
        return memberService.doLogin(member);
    }

}
