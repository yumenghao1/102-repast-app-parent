package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.*;

@Table(name = "ums_shop_facility")
public class ShopFacility extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    private String title;

    private String description;

    private String icon;

}