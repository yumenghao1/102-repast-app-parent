package com.aaa.lee.app.base;

import com.aaa.lee.app.page.PageInfos;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/18 16:32
 * @Description
 *      所有的service都必须要依赖于baseService(只要是简单的单表增删改查都不再需要写了，直接调用即可)
 *      就算是单表的增删改查也必须要用mapper
 **/
public abstract class BaseService<T> {

    /**
     * @author Seven Lee
     * @description
     *      BaseService这个类是需要被所有的service层继承的
     *      继承后直接需要实现未实现的方法
     * @param []
     * @date 2019/12/18
     * @return tk.mybatis.mapper.common.Mapper<T>
     * @throws
    **/
    public abstract Mapper<T> getMapper();

    /**
     * @author Seven Lee
     * @description
     *      新增一条数据
     * @param [t]
     * @date 2019/12/18
     * @return java.lang.Integer
     * @throws
    **/
    public Integer save(T t) throws Exception {
        return getMapper().insert(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      t对象一定至少有一个值(id)
     *      根据主键更新数据
     * @param [t]
     * @date 2019/12/18
     * @return java.lang.Integer
     * @throws
    **/
    public Integer update(T t) throws Exception {
        return getMapper().updateByPrimaryKey(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      根据主键进行删除
     * @param [key]
     * @date 2019/12/18
     * @return java.lang.Integer
     * @throws
    **/
    public Integer deleteByPrimaryKey(Object key) throws Exception {
        return getMapper().deleteByPrimaryKey(key);
    }

    /**
     * @author Seven Lee
     * @description
     *      根据实体类中的属性进行删除
     * @param [t]
     * @date 2019/12/18
     * @return java.lang.Integer
     * @throws
    **/
    public Integer deletByModel(T t) throws Exception {
        return getMapper().deleteByExample(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      通过主键查询数据
     * @param [key]
     * @date 2019/12/18
     * @return T
     * @throws 
    **/
    public T selectById(Object key) throws Exception {
        return getMapper().selectByPrimaryKey(key);
    }

    /**
     * @author Seven Lee
     * @description
     *      查询所有数据
     * @param []
     * @date 2019/12/18
     * @return java.util.List<T>
     * @throws
    **/
    public List<T> selectAll() throws Exception {
        return getMapper().selectAll();
    }

    /**
     * @author Seven Lee
     * @description
     *      根据属性确定一条数据(必须要有唯一键)
     *      或者是能通过几个属性来确定出一条数据(有且只有一条数据)
     * @param [t]
     * @date 2019/12/18
     * @return T
     * @throws
    **/
    public T selectByModel(T t) throws Exception {
        return getMapper().selectOne(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      通过属性查询数据返回多条数据
     * @param [t]
     * @date 2019/12/18
     * @return java.util.List<T>
     * @throws
    **/
    public List<T> selectByModeCondidation(T t) throws Exception {
        return getMapper().select(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      带条件查询的分页
     *      t:条件查询
     *      先进行条件查询--->List(100)--->进行分页(5)
     * @param [t, pageInfos]
     * @date 2019/12/18
     * @return java.util.List<T>
     * @throws
    **/
    public List<T> selectPage(T t, PageInfos<T> pageInfos) throws Exception {
        return getMapper().selectByRowBounds(t, new RowBounds(pageInfos.getPageNum(), pageInfos.getPageSize()));
    }

    /**
     * @author Seven Lee
     * @description
     *      带条件查询所有条数
     *      select count(1) from user where username like '%zhang%';
     *      如果需要查询所有条数则直接传入null(缺省，如果缺省则直接表示查询所有)
     *
     * @param [t]
     * @date 2019/12/18
     * @return java.lang.Integer
     * @throws
    **/
    public Integer selectCount(T t) throws Exception {
        return getMapper().selectCount(t);
    }

    /**
     * @author Seven Lee
     * @description
     *      带条件的分页查询
     * @param [pageInfos]
     * @date 2019/12/18
     * @return com.github.pagehelper.PageInfo<T>
     * @throws
    **/
    public PageInfo<T> selectPageInfo(PageInfos<T> pageInfos) throws Exception {
        // 1.首先判断pageNum是否等于空--->如果等于空则说明是第一个访问
        if(pageInfos.getPageNum() == null) {
            pageInfos.setPageNum(0);
        }
        // 2.直接使用pageHelper进行分页
        // select * from user limit 0,3
        PageHelper.startPage(pageInfos.getPageNum(), pageInfos.getPageSize());
        // 3.查询所有的数据并返回list集合(该集合就是所有的数据但是没有分页)
        List<T> tList = this.selectByModeCondidation(pageInfos.getT());
        // 4.实现list集合的分页
        PageInfo<T> pageInfo = new PageInfo<T>(tList);
        pageInfo.setTotal(this.selectCount(pageInfos.getT()));
        return pageInfo;
    }

}
