package com.aaa.lee.app.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:49
 * @Description
 *      分页类封装
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PageInfos<T> {

    /**
     * 当前页码数
     */
    private Integer pageNum;

    /**
     * 每一页显示条数
     */
    private Integer pageSize;

    /**
     * 所需要返回的数据
     */
    private T t;

}
