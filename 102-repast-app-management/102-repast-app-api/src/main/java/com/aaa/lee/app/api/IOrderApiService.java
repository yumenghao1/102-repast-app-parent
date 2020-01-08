package com.aaa.lee.app.api;

import com.aaa.lee.app.annotation.TokenAnnocation;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.fallback.OrderFallback;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.vo.OrderVo;
import com.aaa.lee.app.vo.TakeOutVo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:55
 * @Description 如果是简单类型传递数据可以传递多个，但是每一个必须要用@RequestParam
 * 但是如果是包装类型，只能传递一个，也必须要用@RequestBody
 **/
@FeignClient(value = "order-interface-provider", fallbackFactory = OrderFallback.class, configuration = MultipartSupportConfig.class)
public interface IOrderApiService {

    // 添加购物车商品
    @PostMapping("/addProductToCart")
    boolean addProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    // 减少购物车商品
    @PostMapping("/reduceProductToCart")
    boolean reduceProductToCart(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    //清空购物车商品
    @PostMapping("/cleanProductToCart")
    ResultData<List<CartItem>> cleanProductToCart(@RequestParam("token") String token, @RequestBody List<CartItem> cartItems, @RequestParam("status") Integer status);

    //测试
    @PostMapping("/test")
    boolean test(@RequestParam("token") String token);

    // 获取购物车内商品
    @PostMapping("/getCartItemList")
    ResultData<List<CartItem>> getCartItemList(@RequestParam("token") String token, @RequestBody CartItem cartItem);

    // 拉取购物车商品信息跳到下单界面
    @PostMapping("/getOrderList")
    TakeOutVo getOrderList(@RequestParam("token") String token, @RequestParam("orderType") Integer orderType, @RequestBody CartItem cartItem);

    // 算钱
    @PostMapping("/getProductAndCoupon")
    Integer getProductAndCoupon(@RequestParam("token") String token, @RequestParam("price") Integer price, @RequestBody Coupon coupon);

    // 微信支付
    @PostMapping("/wxPay")
    Map<String, Object> wxPay(@RequestParam("token") String token, @RequestParam("openid") String openid, @RequestParam("orderSn") String orderSn, @RequestParam("amount") Float amount);

    // 进行下单
    @PostMapping("/insertOrder")
    boolean insertOrder(@RequestParam("token") String token, @RequestBody OrderVo orderVo);

    // 根据个人id查询所有的订单
    @PostMapping("/getOrderListByMemberId")
    List<Order> getOrderListByMemberId(@RequestParam("token") String token, @RequestParam("memberId") Long memberId, @RequestParam("status") Integer status);

    // 根据订单编号查询订单以及订单详情
    @PostMapping("/getOrderVoByOrderSn")
    OrderVo getOrderVoByOrderSn(@RequestParam("token") String token, @RequestParam("orderSn") String orderSn);

    // 提交退货申请
    @PostMapping(value = "/addReturnApply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    Integer addReturnApply(@RequestParam("token") String token, @RequestParam("orderSn") String orderSn, @RequestParam("reason") String reason, @RequestPart("files") MultipartFile[] files);
    // 确认收货
    @PostMapping("/affirmOrder")
    boolean affirmOrder(@RequestParam("token") String token,@RequestParam("orderSn")  String orderSn) ;
}
