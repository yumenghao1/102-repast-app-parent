import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.model.OrderItem;
import com.aaa.lee.app.utils.DateUtil;
import com.aaa.lee.app.utils.JSONUtil;
import com.aaa.lee.app.vo.OrderVo;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class test1 {
    public static void main(String[] args) {
        OrderVo orderVo = new OrderVo();
        Order order = new Order();
        List<OrderItem> OrderItems = new ArrayList<>();
        order.setMemberId(1l)
                .setShopId(1l)
                .setCouponId(1l)
                .setOrderSn("ordersn")
                .setCreateTime(Timestamp.valueOf(DateUtil.getDateNow()))
                .setMemberUsername("于梦豪")
                .setTotalAmount(BigDecimal.valueOf(100))
                .setPayAmount(BigDecimal.valueOf(95))
                .setFreightAmount(BigDecimal.valueOf(250))
                .setPromotionAmount(BigDecimal.valueOf(0))
                .setIntegrationAmount(BigDecimal.valueOf(0))
                .setCouponAmount(BigDecimal.valueOf(5))
                .setDiscountAmount(BigDecimal.valueOf(0))
                .setPayType(1)
                .setSourceType(1)
                .setStatus(0)
                .setOrderType(1)
                .setDeliveryCompany("物流")
                .setDeliverySn("无敌")
                .setAutoConfirmDay(7)
                .setIntegration(50)
                .setGrowth(50)
                .setPromotionInfo("大活动")
                .setBillType(0)
                .setBillHeader("布吉岛")
                .setBillContent("布吉岛")
                .setBillReceiverPhone("18595917116")
                .setBillReceiverEmail("3356541509@qq.com")
                .setReceiverName("于梦豪")
                .setReceiverPhone("250")
                .setReceiverPostCode("50000")
                .setReceiverProvince("河南省")
                .setReceiverCity("郑州市")
                .setReceiverRegion("金水区")
                .setReceiverDetailAddress("河南省郑州市金水区牛顿国际aaa教育")
                .setNote("加水")
                .setConfirmStatus(0)
                .setDeleteStatus(0)
                .setUseIntegration(50)
                .setPaymentTime(Timestamp.valueOf(DateUtil.getDateNow()))
                .setDeliveryTime(null)
                .setReceiveTime(null)
                .setCommentTime(null)
                .setModifyTime(Timestamp.valueOf(DateUtil.getDateNow()));
        orderVo.setOrder(order);
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderSn(order.getOrderSn())
                .setOrderId(order.getId())
                .setProductId(1L)
                .setProductPic("http://www.aaa.com/aaa")
                .setProductName("aaa软件教育")
                .setProductBrand("aaa")
                .setProductSn("布吉岛")
                .setProductPrice(BigDecimal.valueOf(100))
                .setProductQuantity(1)
                .setProductSkuId(1L)
                .setProductSkuCode("布吉岛")
                .setProductCategoryId(1L)
                .setSp1("sp1").setSp2("sp2").setSp3("sp3")
                .setPromotionName("aaa")
                .setPromotionAmount(BigDecimal.valueOf(1))
                .setIntegrationAmount(BigDecimal.valueOf(1))
                .setCouponAmount(BigDecimal.valueOf(5))
                .setRealAmount(BigDecimal.valueOf(5))
                //商品积分和商品成长值
                .setGiftIntegration(50)
                .setGiftGrowth(50)
                .setProductAttr("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        OrderItems.add(orderItem);
        orderVo.setOrderItemList(OrderItems);
        String s = JSONUtil.toJsonString(orderVo);
        System.out.println(s);
    }
}
