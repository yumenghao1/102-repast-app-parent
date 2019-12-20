package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.mapper.CartItemMapper;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.utils.DateUtil;
import org.apache.commons.httpclient.util.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
     * @param stock
     * @return java.lang.Boolean
     * @throws
     * @author Seven Lee
     * @description 加入购物车
     * @date 2019/12/19
     **/
    public boolean addProductToCart(CartItem cartItem, Long stock) {
        int result = 0;
        if (stock > 0) {
            CartItem newCartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), cartItem.getDeleteStatus());
            if (null != newCartItem) {
                result = cartItemMapper.updateByPrimaryKey(upDateCartItem(newCartItem, cartItem.getQuantity()));
            } else {
                result = cartItemMapper.insert(cartItem.setCreateDate(Timestamp.valueOf(DateUtil.getDateNow())));
            }
        }
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * @return java.lang.Boolean
     * @throws
     * @author Seven Lee
     * @description 查询该商品是否在购物车中
     * @date 2019/12/19
     **/
    private CartItem findByProuct(Long memberId, Long productId, Integer deleteStatus) {
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", productId).andEqualTo("memberId", memberId).andEqualTo("deleteStatus", deleteStatus);
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        if (cartItems.size() > 0) {
            return cartItems.get(0);
        }
        return null;
    }

    /**
     * 对购物车商品数量进行修改
     *
     * @param oldCartItem
     * @param quantity
     * @return
     */
    private CartItem upDateCartItem(CartItem oldCartItem, Integer quantity) {
        return  oldCartItem.setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())).setQuantity(oldCartItem.getQuantity() + quantity);
    }
}
