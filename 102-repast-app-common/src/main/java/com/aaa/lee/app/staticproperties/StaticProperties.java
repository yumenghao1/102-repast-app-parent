package com.aaa.lee.app.staticproperties;

import java.math.BigDecimal;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:03
 * @Description
 **/
public class StaticProperties {

    // 通用常量
    public static final String OK = "OK";
    // wxpay的map的key
    public static final String PAY_MAP_KEY = "errMsg";
    // 异常
    public static final String GETTAKEOUTEXCEPTION = "拉取外卖列表出现异常";
    // 是否删除
    public static final Integer DEL_STATUS = 2;
    // 是否删除
    public static final Integer NOT_DEL_STATUS = 1;
    // 没有库存
    public static final Integer NO_STOCK = 0;
    // 外卖
    public static final Integer TAKEOUT = 1;
    // 到店
    public static final Integer SHOP = 2;
    // 预约
    public static final Integer APPOINTMENT = 3;
    // 拼团
    public static final Integer TOURDIY = 4;
    //微信支付
    public static final Integer WX_PAY = 1;
    //到店支付
    public static final Integer SHOP_PAY = 2;
    // 运费金额
    public static final BigDecimal FREIGHT = BigDecimal.valueOf(10);
    // 订单来源 1代表app
    public static final Integer SOURCETYPE = 1;
    // 待付款
    public static final Integer NO_PAY = 0;
    // 待发货
    public static final Integer PENDING = 1;
    // 发货中
    public static final Integer DELIVERY = 2;
    // 收货成功
    public static final Integer SUCCESS = 3;
    // 无效订单
    public static final Integer INVALID = 4;
    // 默认的物流公司
    public static final String DELIVERY_COMPANY = "梦工厂物流公司";
    // 自动的确认收货时间
    public static final Integer AUTOCONFIRMDAY=7;
    // 默认的积分和成长值
    public static final  Integer INTEGRATION =50;
    // 默认的邮编
    public static final String  POSTCODE="000000";
    // 默认的收货状态 未收货
    public static final Integer NO_CONFIRM_STATUS =0;
    // 确认收货
    public static final Integer CONFIRM_STATUS =1;
    // 未删除
    public static final Integer NO_DELETE =0;
    // 删除
    public static final Integer DELETE =1;


}
