package com.aaa.lee.app.model;

import com.aaa.lee.app.base.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Table(name = "ums_agent_informaion")
public class AgentInformaion extends BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String company;

    @Column(name = "business_license")
    private String businessLicense;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    private Integer status;

    /**
     * 合同ID
     */
    @Column(name = "contract_id")
    private Long contractId;

}