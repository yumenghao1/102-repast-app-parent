package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.mapper.CartItemMapper;
import com.aaa.lee.app.mapper.MemberMapper;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Member;
import com.aaa.lee.app.utils.IDUtil;
import com.aaa.lee.app.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:00
 * @Description
 **/
@Service
public class CartService extends BaseService<CartItem> {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Override
    public Mapper<CartItem> getMapper() {
        return cartItemMapper;
    }

    /**
     * @param
     * @return java.lang.Boolean
     * @throws
     * @author Seven Lee
     * @description 执行加入购物车方法
     * @date 2019/12/19
     **/

    public boolean addProductToCart(CartItem cartItem, String token) {
        return true;
    }


}
