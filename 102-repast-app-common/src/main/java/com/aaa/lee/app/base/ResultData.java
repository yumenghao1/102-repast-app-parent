package com.aaa.lee.app.base;

import lombok.*;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:04
 * @Description
 *      如果需要使用以下@Data注解必须先添加jar包
 *       <dependency>
 *             <groupId>org.projectlombok</groupId>
 *             <artifactId>lombok</artifactId>
 *         </dependency>
 *       lombok：简化了getter和setter
 *       @Data:就是getter和setter
 *
 *       统一controller的返回值，也就是说所有的controller统一返回ResultData
 *       --->code:代表状态码
 *       --->msg:返回消息
 *       --->detail:详细描述
 *       --->T:所需要返回的属性
 **/
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ResultData<T> {

    private String code;
    private String msg;
    private String detail;
    private T data;

}
