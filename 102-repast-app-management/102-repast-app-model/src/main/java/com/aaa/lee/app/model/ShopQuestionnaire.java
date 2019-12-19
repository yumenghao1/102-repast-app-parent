package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Table(name = "ums_shop_questionnaire")
public class ShopQuestionnaire extends BaseModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 问卷问题
     */
    private String question;

    /**
     * 业务点：1. 用户反馈的问卷
     */
    private Integer type;

    private Integer sort;

}