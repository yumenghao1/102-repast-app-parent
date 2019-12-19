package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
@Table(name = "ums_contract")
public class Contract implements Serializable {
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