package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.*;

@Table(name = "ums_contract")
public class Contract extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 签订者ID
     */
    @Column(name = "owner_id")
    private Long ownerId;

    /**
     * 合同类型
     */
    private Integer type;

    /**
     * 合同模版ID
     */
    @Column(name = "template_id")
    private Long templateId;

    /**
     * json格式，具体格式跟合同模块对应
     */
    private String content;

}