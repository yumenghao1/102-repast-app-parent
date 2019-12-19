package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "ums_message")
public class Message extends BaseModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Integer shopId;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 该字段不为null 时为接受消息的用户ID
     */
    @Column(name = "send_to")
    private Long sendTo;

    /**
     * 1. 小程序消息 2. 后天管理系统消息
     */
    private Integer channel;

}