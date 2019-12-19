package com.aaa.lee.app.base;

import com.aaa.lee.app.status.LoginStatus;
import org.springframework.stereotype.Controller;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:09
 * @Description
 *      统一使用该controller返回
 *      也就是说以后所有的controller都不必须要继承BaseController
 **/
@Controller
public class BaseController {

    /**
     * @author Seven Lee
     * @description
     *      统一返回值，登录成功，使用系统消息返回
     * @param []
     * @date 2019/12/18
     * @return com.aaa.lee.app.base.ResultData
     * @throws 
    **/
    protected ResultData success() {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      统一返回值，登录成功，自定义返回消息
     * @param []
     * @date 2019/12/18
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      统一返回值，登录成功，使用系统消息返回并返回数据
     * @param []
     * @date 2019/12/18
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      统一返回值，登录成功，自定义返回消息并且返回结果
     * @param []
     * @date 2019/12/18
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(Object data, String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      登录失败，返回系统消息
     * @param []
     * @date 2019/12/18
     * @return com.aaa.lee.app.base.ResultData
     * @throws
    **/
    protected ResultData failed() {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_FAILED.getCode());
        resultData.setMsg(LoginStatus.LOGIN_FAILED.getMsg());
        return resultData;
    }

    // TODO 暂时未完成，需要什么方法自己添加

}
