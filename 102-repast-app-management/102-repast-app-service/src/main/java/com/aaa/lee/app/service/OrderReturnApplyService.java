package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.mapper.OrderReturnApplyMapper;
import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.model.OrderItem;
import com.aaa.lee.app.model.OrderReturnApply;
import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.utils.DateUtil;
import com.aaa.lee.app.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderReturnApplyService extends BaseService {
    @Autowired
    private OrderReturnApplyMapper orderReturnApplyMapper;

    @Override
    public Mapper getMapper() {
        return orderReturnApplyMapper;
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 用户提交退货申请
     * @date create in 2019/12/26 20:19
     **/
    @Transactional(rollbackFor = Exception.class)
    public Integer addOrderReturnApply(String orderSn, MultipartFile[] files, String reason, OrderService orderService, UploadService uploadService) {

        // 第一步，查询订单以及订单详情信息
        OrderVo orderVo = orderService.getOrderByOrderSn(orderSn, null);
        System.out.println(orderVo.toString());
        // 创建对象进行添加数据
        OrderReturnApply orderReturnApply = new OrderReturnApply();
        // 第二步进行上传凭证
        if (null != orderVo && null != files) {
            Order order = orderVo.getOrder();
            Map<String, Object> upload = uploadService.upload(files, order.getId());
            // 凭证上传成功后进行提交退货申请
            if (null != upload && upload.get("code").equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
                orderReturnApply.setOrderId(order.getId())
                        .setOrderSn(orderSn)
                        .setMemberUsername(order.getMemberUsername())
                        .setShopId(order.getShopId())
                        .setProofPics(String.valueOf(upload.get("data")))
                        .setCreateTime(Timestamp.valueOf(DateUtil.getDateNow()))
                        .setReason(reason).setStatus(StaticProperties.NO_CONFIRM_STATUS);
                try {
                    return save(orderReturnApply);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("退货申请出现异常");
                }

            }
        }
        return 0;
    }


//    /**
//     * 把查到的商品名称和从订单中查询到的信息加入到OrderReturnApply对象中
//     *
//     * @param orderReturnApply
//     * @param orderInfoForReturnApply
//     * @param shopName
//     */
//    private void settingRAInfoWithOrderInfoAndShopName(OrderReturnApply orderReturnApply, Order orderInfoForReturnApply, String shopName) {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            orderReturnApply.setShopId(orderInfoForReturnApply.getShopId())
//                    .setOrderSn(orderInfoForReturnApply.getOrderSn())
//                    .setMemberUsername(orderInfoForReturnApply.getMemberUsername())
//                    .setReturnName(orderInfoForReturnApply.getReceiverName())
//                    .setReturnPhone(orderInfoForReturnApply.getReceiverPhone())
//                    .setReceiveMan(shopName)
//                    .setReceiveTime(orderInfoForReturnApply.getReceiveTime())
//                    .setCreateTime(sdf.parse(sdf.format(new Date())))
//                    .setStatus(0);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 把从订单明细中查到的要退货的商品信息加入到OrderReturnApply对象中
//     *
//     * @param orderReturnApply
//     * @param orderItem
//     * @return
//     */
//    private OrderReturnApply settingRAInfoWithProductInfoInOrderItem(OrderReturnApply orderReturnApply, OrderItem orderItem) {
//        orderReturnApply.setProductId(orderItem.getProductId())
//                .setProductPic(orderItem.getProductPic())
//                .setProductName(orderItem.getProductName())
//                .setProductBrand(orderItem.getProductBrand())
//                .setProductAttr(orderItem.getProductAttr())
//                .setProductCount(orderItem.getProductQuantity())
//                .setProductPrice(orderItem.getProductPrice())
//                .setProductRealPrice(orderItem.getRealAmount())
//                .setReturnAmount(orderItem.getRealAmount());
//        return orderReturnApply;
//    }
//
//
//    /**
//     * 将要退货的商品生成相应的退货申请列表(returnApplyList)
//     *
//     * @param returnApply
//     * @param returnProductList
//     * @return
//     */
//    private List<OrderReturnApply> generateReturnApplyList(OrderReturnApply returnApply, List<OrderItem> returnProductList) {
//        List<OrderReturnApply> returnApplyList = new ArrayList<OrderReturnApply>();
//        for (OrderItem orderItem : returnProductList) {
//            OrderReturnApply apply = settingRAInfoWithProductInfoInOrderItem(returnApply, orderItem);
//            returnApplyList.add(apply);
//        }
//        return returnApplyList;
//    }
//
//    /**
//     * 根据订单id查询退货申请状态
//     * 0->待处理; 1->退货中; 2->已完成; 3->已拒绝
//     *
//     * @param orderId
//     * @return
//     */
//    public Integer getReturnApplyStatusByOrderId(Long orderId) {
//        OrderReturnApply returnApply = new OrderReturnApply().setOrderId(orderId);
//        List<OrderReturnApply> returnApplyList = orderReturnApplyMapper.select(returnApply);
//        Integer returnApplyStatus = returnApplyList.get(0).getStatus();
//        return returnApplyStatus;
//    }
//
//    /**
//     * 通过orderId查询退货申请详情
//     *
//     * @param orderId
//     * @return
//     */
//    public List<OrderReturnApply> getReturnApplyInfoByOrderId(Long orderId) {
//        List<OrderReturnApply> returnApplyList = orderReturnApplyMapper.select(new OrderReturnApply().setOrderId(orderId));
//        return returnApplyList;
//    }

}
