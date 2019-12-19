package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "oms_order_return_reason")
public class OrderReturnReason implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 退货类型
     */
    private String name;

    private Integer sort;

    /**
     * 状态：0->不启用；1->启用
     */
    private Integer status;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;
}