package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.mapper.CartItemMapper;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.DateUtil;
import org.apache.commons.httpclient.util.DateParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
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
     * @description 查询该商品的信息
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
        return oldCartItem.setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())).setQuantity(oldCartItem.getQuantity() + quantity);
    }

    /**
     * 减少购物车商品数量
     *
     * @param cartItem
     * @return
     */
    public ResultData reduceProductToCart(CartItem cartItem) {
        int count = 0;
        boolean status = false;
        ResultData resultData = new ResultData();
        // 查询该商品的数量是否为1，如果为一，修改数量并逻辑删除和修改时间,前台必须传111111111111del
        CartItem newCartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), cartItem.getDeleteStatus());
        // 先判断查询到没有，在判断是否为超时商品，如果为超时商品不会在操控库存
        if (null != newCartItem) {
            status = isTimeout(newCartItem);
            // 判断是否存在该商品在购物车的数量
            if (newCartItem.getQuantity() <= cartItem.getQuantity()) {
                // 修改数量和状态
                count = cartItemMapper.updateByPrimaryKey(newCartItem.setQuantity(0).setDeleteStatus(Integer.valueOf(StatusEnum.FAILED.getCode())).setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())));
            } else {
                // 修改数量
                count = cartItemMapper.updateByPrimaryKey(upDateCartItem(newCartItem, -cartItem.getQuantity()));
            }
        }
        // 操作成功进行返回结果
        if (count > 0) {
            return resultData.setCode(StatusEnum.SUCCESS.getCode()).setMsg(StatusEnum.SUCCESS.getMsg()).setData(status);
        }
        return resultData.setCode(StatusEnum.FAILED.getCode());
    }

    /**
     * 判断是否为超时商品
     *
     * @param newCartItem
     * @return
     */
    private boolean isTimeout(CartItem newCartItem) {
        Timestamp timestamp = Timestamp.valueOf(DateUtil.getDateNow());
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", newCartItem.getProductId())
                .andEqualTo("memberId", newCartItem.getMemberId())
                .andEqualTo("deleteStatus", newCartItem.getDeleteStatus())
                .andBetween("modifyDate", new Timestamp(System.currentTimeMillis() - 600000), Timestamp.valueOf(DateUtil.getDateNow()));
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        if (cartItems.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 清空购物车
     *
     * @param cartItems
     * @return
     */
    public ResultData cleanProductToCart(List<CartItem> cartItems) {
        List<CartItem> newCarItems = new ArrayList<>();
        ResultData resultData = new ResultData();
        boolean b = true;
        // 查询出所有购物车商品，然后判断是否为失效购物车商品，这些不是失效的商品都是必要要操控库存的
        for (CartItem cartItem : cartItems) {
            // 查询该商品在购物车的信息，有多少件，各种信息，做数据更新
            cartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), cartItem.getDeleteStatus());
            // 不为空然后就先把要修改库存的数据放到list中返回
            if (null != cartItem) {
                if (isTimeout(cartItem)) {
                    newCarItems.add(cartItem);
                }
                // 逻辑删除购物车商品
                int i = cartItemMapper.updateByPrimaryKey(cartItem.setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())).setDeleteStatus(2).setQuantity(0));
                // 设置一个为true的boolean，只有失败才会变为false
                if (i < 0) {
                    b = false;
                }
            }
        }
        if (b) {
            return resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode()).setMsg(StatusEnum.SUCCESS.getMsg()).setData(newCarItems);
        } else {
            return resultData.setCode(LoginStatus.LOGIN_FAILED.getCode()).setMsg(StatusEnum.FAILED.getMsg());
        }
    }
}
