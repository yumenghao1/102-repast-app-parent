package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.*;

@Table(name = "sms_home_recommend_product")
public class HomeRecommendProduct extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 产品ID
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * 产品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 推荐状态，1推荐，0不再推荐
     */
    @Column(name = "recommend_status")
    private Integer recommendStatus;

    /**
     * 排序字段
     */
    private Integer sort;

}