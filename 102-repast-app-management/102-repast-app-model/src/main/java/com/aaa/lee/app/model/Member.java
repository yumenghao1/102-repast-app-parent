package com.aaa.lee.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Seven Lee
 * @description
 *      @Table(name = "ums_member")
 *      @Id
 *      JPA:因为hibernate自带这些注解
 *      和数据库表进行映射
 * @param
 * @date 2019/12/19
 * @return 
 * @throws 
**/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "ums_member")
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /*@Size(max = 200, message = "当id超过数据库最大值的时候直接会友好的提示，并不是抛出各种异常")
    @NotNull*/
    private Long id;

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "member_level_id")
    private Long memberLevelId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 帐号启用状态:0->禁用；1->启用
     */
    private Integer status;

    /**
     * 注册时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 头像
     */
    private String icon;

    /**
     * 性别：0->未知；1->男；2->女
     */
    private Integer gender;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 所做城市
     */
    private String city;

    /**
     * 职业
     */
    private String job;

    /**
     * 个性签名
     */
    @Column(name = "personalized_signature")
    private String personalizedSignature;

    /**
     * 用户来源
     */
    @Column(name = "source_type")
    private Integer sourceType;

    /**
     * 积分
     */
    private Integer integration;

    /**
     * 成长值
     */
    private Integer growth;

    /**
     * 剩余抽奖次数
     */
    @Column(name = "luckey_count")
    private Integer luckeyCount;

    /**
     * 历史积分数量
     */
    @Column(name = "history_integration")
    private Integer historyIntegration;

    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 微信返回的open_id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信返回的session_key
     */
    @Column(name = "session_key")
    private String sessionKey;

    /**
     * 登录验证token
     */
    private String token;
}