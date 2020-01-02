package com.aaa.lee.app.service;

import com.aaa.lee.app.Myconst.WXConst;
import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.mapper.OrderMapper;
import com.aaa.lee.app.model.*;
import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.DateUtil;
import com.aaa.lee.app.utils.IDUtil;
import com.aaa.lee.app.utils.PayUtil;
import com.aaa.lee.app.utils.StringUtil;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sun.swing.plaf.synth.DefaultSynthStyle;
import tk.mybatis.mapper.common.Mapper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: YMH
 * @CreateDate: 2019/12/24 23:37
 * @Version:
 */
@Service
public class OrderService extends BaseService<Order> {

    @Autowired
    private OrderMapper orderMapper;


    @Override
    public Mapper<Order> getMapper() {
        return orderMapper;
    }

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
    public TakeOutVo getOrderList(String token, CartItem cartItem, Integer orderType, CartService cartService, IRepastService repastServiceFegin, IShopApiService shopServiceFegin) throws Exception {
        TakeOutVo takeOutVo = new TakeOutVo();
        takeOutVo.setOrderType(orderType);
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
        // 如果是外卖拉取收货地址
        if (orderType.equals(StaticProperties.TAKEOUT)) {
            // 根据用户id查询个人的收货地址列表,即便调用失败也不影响下单
            ResultData<List<MemberReceiveAddress>> adrResultData = repastServiceFegin.getAdrByMemberId(token, memberId);
            if (null != adrResultData && adrResultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
                if (null != adrResultData.getData()) {
                    takeOutVo.setMemberReceiveAddressList(adrResultData.getData());
                }
            } else {
                throw GetTakeOutException;
            }
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

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 添加订单以及订单详情
     * @date create in 2019/12/25 15:43
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrder(OrderVo orderVo, OrderItemService orderItemService, OrderOperateHistoryService orderOperateHistoryService) {
        try {
            boolean b = true;
            Order order = orderVo.getOrder();
            // 获取随机订单号
            String OrderSn = StringUtil.getCharAndNum(StaticProperties.DEL_STATUS) + IDUtil.getUUID();
            order.setOrderSn(OrderSn);
            // 获取当前时间作为下单时间和第一次修改时间
            Timestamp timestamp = Timestamp.valueOf(DateUtil.getDateNow());
            order.setCreateTime(timestamp);
            order.setModifyTime(timestamp);
            // 如果是外卖，拼团，预约则为微信1，如果点餐，则为2
            Integer orderType = order.getOrderType();
            Integer payType;
            if (orderType.equals(StaticProperties.TAKEOUT) || orderType.equals(StaticProperties.APPOINTMENT) || orderType.equals(StaticProperties.TOURDIY)) {
                payType = StaticProperties.WX_PAY;
            } else {
                payType = StaticProperties.SHOP_PAY;
            }
            order.setPayType(payType);
            // 默认邮费
            order.setFreightAmount(StaticProperties.FREIGHT);
            // 默认的是1 app支付
            order.setSourceType(StaticProperties.SOURCETYPE);
            // 默认的状态是待付款
            order.setStatus(StaticProperties.NO_PAY);
            // 默认的物流公司
            order.setDeliveryCompany(StaticProperties.DELIVERY_COMPANY);
            // 随机的物流单号
            String delivery_sn = IDUtil.getUUID();
            order.setDeliverySn(delivery_sn);
            //设置默认的自动确认收货时间
            order.setAutoConfirmDay(StaticProperties.AUTOCONFIRMDAY);
            // 设置默认的成长值和积分
            order.setIntegration(StaticProperties.INTEGRATION);
            order.setGrowth(StaticProperties.INTEGRATION);
            // 设置默认的发票类型
            order.setBillType(StaticProperties.NO_PAY);
            //设置默认的邮政编号
            order.setReceiverPostCode(StaticProperties.POSTCODE);
            //设置默认的收货状态
            order.setConfirmStatus(StaticProperties.NO_CONFIRM_STATUS);
            // 默认订单为有效状态
            order.setDeleteStatus(StaticProperties.NOT_DEL_STATUS);
            // 第一步添加订单到订单表，返回id进行添加到订单详情表
            Integer save = super.save(order);
            if (save > 0) {
                // 便利商品详情进行添加订单详情
                List<OrderItem> orderItems = orderVo.getOrderItemList();
                for (OrderItem orderItem : orderItems) {
                    // 设置订单id和编号存入订单详情表中
                    orderItem.setOrderId(order.getId());
                    orderItem.setOrderSn(order.getOrderSn());
                    if (orderItemService.save(orderItem) <= 0) {
                        b = false;
                    }
                }
                if (b) {
                    OrderOperateHistory orderOperateHistory = new OrderOperateHistory();
                    orderOperateHistory.setOrderId(order.getId())
                            .setOrderStatus(StaticProperties.NO_STOCK)
                            .setCreateTime(Timestamp.valueOf(DateUtil.getDateNow()))
                            .setOperateMan(StatusEnum.SUCCESS.getCode())
                            .setShopId(order.getShopId()).setNote(order.getNote());
                    Integer result = orderOperateHistoryService.save(orderOperateHistory);
                    if (result > 0) {
                        return true;
                    }
                }
                throw new RuntimeException("新增订单抛出异常");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("新增订单抛出异常");
        }


    }


    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 个人用户 查询自己的所有订单
     * @date create in 2019/12/26 16:29
     **/
    public List<Order> getListOrderByMemberId(Long memberId, Integer status) {
        try {
            Order order = new Order().setMemberId(memberId);
            if (null != status) {
                order.setStatus(status);
            }
            return super.selectByModeCondidation(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据订单号查询订单以及订单详情
     *
     * @param orderSn
     * @return
     */
    public OrderVo getOrderByOrderSn(String orderSn, OrderItemService orderItemService) {
        try {
            Order order = super.selectByModel(new Order().setOrderSn(orderSn));
            if (null != order && null != orderItemService) {
                List<OrderItem> orderItems = orderItemService.selectByModeCondidation(new OrderItem().setOrderSn(orderSn));
                if (null != orderItems) {
                    return new OrderVo().setOrder(order).setOrderItemList(orderItems);
                }
            } else {
                if (null != order) {
                    return new OrderVo().setOrder(order);
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
