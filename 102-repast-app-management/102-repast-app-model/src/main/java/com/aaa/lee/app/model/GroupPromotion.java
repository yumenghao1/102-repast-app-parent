package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "sms_group_promotion")
public class GroupPromotion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    private String title;

    /**
     * 团购活动的图片
     */
    private String images;

    /**
     * 开始日期
     */
    @Column(name = "start_date")
    private Date startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    private Date endDate;

    /**
     * 上下线状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * n人团
     */
    private Byte type;

    /**
     * 未成团，自动延期n天
     */
    @Column(name = "auto_delay")
    private Integer autoDelay;

    /**
     * 团购价格
     */
    private Long price;

    /**
     * 原价
     */
    @Column(name = "original_price")
    private Long originalPrice;

    /**
     * 1是自动成团，拼团时间到，不管人数是否到，自动开团
     */
    @Column(name = "auto_open")
    private Byte autoOpen;

}