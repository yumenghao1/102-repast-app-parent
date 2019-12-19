package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.*;

@Table(name = "sms_group_promotion_product_relation")
public class GroupPromotionProductRelation extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 团购活动id
     */
    @Column(name = "group_promotion_id")
    private Integer groupPromotionId;

    /**
     * 商品ID
     */
    @Column(name = "product_id")
    private Integer productId;

    /**
     * 产品数量
     */
    private Integer count;


}