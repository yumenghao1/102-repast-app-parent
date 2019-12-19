package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "cms_member_report")
public class MemberReport implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    /**
     * 举报类型：0->商品评价；1->话题内容；2->用户评论
     */
    @Column(name = "report_type")
    private Integer reportType;

    /**
     * 举报人
     */
    @Column(name = "report_member_name")
    private String reportMemberName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "report_object")
    private String reportObject;

    /**
     * 举报状态：0->未处理；1->已处理
     */
    @Column(name = "report_status")
    private Integer reportStatus;

    /**
     * 处理结果：0->无效；1->有效；2->恶意
     */
    @Column(name = "handle_status")
    private Integer handleStatus;

    private String note;


}