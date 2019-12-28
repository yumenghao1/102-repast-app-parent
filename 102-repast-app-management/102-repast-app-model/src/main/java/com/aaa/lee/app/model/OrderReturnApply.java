package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "oms_order_return_apply")
public class OrderReturnApply implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 订单id
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 收货地址
     */
    @Column(name = "company_address")
    private String companyAddress;

    /**
     * 订单编号
     */
    @Column(name = "order_sn")
    private String orderSn;

    /**
     * 申请时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 会员用户名
     */
    @Column(name = "member_username")
    private String memberUsername;

    /**
     * 退款金额
     */
    @Column(name = "return_amount")
    private BigDecimal returnAmount;

    /**
     * 退货人姓名
     */
    @Column(name = "return_name")
    private String returnName;

    /**
     * 退货人电话
     */
    @Column(name = "return_phone")
    private String returnPhone;

    /**
     * 申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝
     */
    private Integer status;

    /**
     * 处理时间
     */
    @Column(name = "handle_time")
    private Date handleTime;

    /**
     * 原因
     */
    private String reason;

    /**
     * 描述
     */
    private String description;

    /**
     * 凭证图片，以逗号隔开
     */
    @Column(name = "proof_pics")
    private String proofPics;

    /**
     * 处理备注
     */
    @Column(name = "handle_note")
    private String handleNote;

    /**
     * 处理人员
     */
    @Column(name = "handle_man")
    private String handleMan;

    /**
     * 收货人
     */
    @Column(name = "receive_man")
    private String receiveMan;

    /**
     * 收货时间
     */
    @Column(name = "receive_time")
    private Date receiveTime;

    /**
     * 收货备注
     */
    @Column(name = "receive_note")
    private String receiveNote;

}