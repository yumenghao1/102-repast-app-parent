package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
@ToString
@Accessors(chain = true)
@Table(name = "ums_shop_questionnaire_answer")
public class ShopQuestionnaireAnswer implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 问卷ID
     */
    @Column(name = "question_id")
    private Long questionId;

    private String title;

    /**
     * 问卷的回答
     */
    private String answer;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 店铺ID
     */
    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 0-5星
     */
    private Byte star;


}