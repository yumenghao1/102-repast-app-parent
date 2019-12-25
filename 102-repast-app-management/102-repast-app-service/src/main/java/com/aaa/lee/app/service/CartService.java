package com.aaa.lee.app.service;

import com.aaa.lee.app.api.IShopApiService;
import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.mapper.CartItemMapper;
import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.staticproperties.StaticProperties;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import com.aaa.lee.app.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
     * @return
     * @throws
     * @author YMH
     * @description 添加购物车
     * @date create in 2019/12/25 1:25
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean addProductToCart(CartItem cartItem, IShopApiService iShopApiService) {
        // 受影响的行数
        int result = 0;
        // 判断是否超时
        boolean istimeout = true;
        // 进行更新库存的实体类
        CartItem newCartItem = new CartItem();
        // 参数不为空，将参数放入carItem
        if (null != cartItem) {
            newCartItem.setMemberId(cartItem.getMemberId());
        } else {
            return false;
        }
        try {
            synchronized (this) {
                // 查询库存
                Long stock = iShopApiService.getProductStockById();
                if (stock > 0) {
                    // 进入这进行购物车加，库存减少，至于减多少，需要进行判断，该商品是锁定库存还是未锁定库存的商品，
                    //如果该商品已经锁定库存，就直接减1，如果该商品是未锁定的，就直接减去（已有的加上1）
                    newCartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), StaticProperties.NOT_DEL_STATUS);
                    if (null != newCartItem) {
                        // true就是锁定库存的
                        istimeout = isTimeout(cartItem);
                        result = update(upDateCartItem(newCartItem, cartItem.getQuantity()));
                    } else {
                        result = save(cartItem.setCreateDate(Timestamp.valueOf(DateUtil.getDateNow())));
                    }
                    if (result > 0) {
                        ResultData resultData;
                        if (istimeout) {
                            // 添加（该新增商品是锁定库存的商品）和新增 都进入该方法,进行修改库存
                            resultData = iShopApiService.updateProductStock(cartItem.getProductId(), cartItem.getQuantity());
                        } else {
                            // 添加进入该方法，商品为未锁定库存
                            resultData = iShopApiService.updateProductStock(cartItem.getProductId(), cartItem.getQuantity() + newCartItem.getQuantity());
                        }
                        return updateStockStatus(resultData);
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加购物车失败抛出异常");
        }
    }

    /**
     * @param
     * @return
     * @throws
     * @author YMH
     * @description 修改状态
     * @date create in 2019/12/25 12:50
     **/
    private boolean updateStockStatus(ResultData resultData) throws Exception {
        if (null != resultData && resultData.getCode().equals(LoginStatus.LOGIN_SUCCESS.getCode())) {
            return true;
        } else {
            throw new RuntimeException("添加购物车失败抛出异常");
        }
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
     * @return java.lang.Boolean
     * @throws
     * @author
     * @description 查询10分钟没有操作的购物车商品进行库存回复
     * @date 2019/12/19
     **/
    public List<CartItem> noPass(Integer deleteStatus) {
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteStatus", deleteStatus).andBetween("modifyDate", new Timestamp(System.currentTimeMillis() - 600000), Timestamp.valueOf(DateUtil.getDateNow()));
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        if (cartItems.size() > 0) {
            return cartItems;
        }
        return null;
    }

    /**
     * 对购物车商品数量进行重新封装
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
    @Transactional(rollbackFor = Exception.class)
    public boolean reduceProductToCart(CartItem cartItem, IShopApiService shopApiService) {
        int count = 0;
        boolean status = true;
        try {
            synchronized (this) {
                // 查询该商品的数量是否为1，如果为一，修改数量并逻辑删除和修改时间,前台必须传1del
                CartItem newCartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), cartItem.getDeleteStatus());
                // 先判断查询到没有，在判断是否为超时商品，如果为超时商品不会在操控库存
                if (null != newCartItem) {
                    status = isTimeout(newCartItem);
                    // 判断是否存在该商品在购物车的数量
                    if (newCartItem.getQuantity() <= cartItem.getQuantity()) {
                        // 修改数量和状态
                        count = cartItemMapper.updateByPrimaryKey(
                                newCartItem.setQuantity(StaticProperties.NO_STOCK)
                                        .setDeleteStatus(StaticProperties.DEL_STATUS)
                                        .setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())));
                    } else {
                        // 修改数量
                        count = cartItemMapper.updateByPrimaryKey(upDateCartItem(newCartItem, -cartItem.getQuantity()));
                    }
                    // 操作成功进行返回结果
                    if (count > 0) {
                        if (status) {
                            return updateStockStatus(shopApiService.updateProductStock(cartItem.getProductId(), cartItem.getQuantity()));
                        } else {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("购物车减少抛出异常");
        }
        return false;
    }

    /**
     * 判断是否为超时商品
     * true是查询到的是锁定库存的
     *
     * @param newCartItem
     * @return
     */
    private boolean isTimeout(CartItem newCartItem) {
        String timestamp1 = DateUtil.formatDate(new Date(System.currentTimeMillis() - 600000));
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("productId", newCartItem.getProductId())
                .andEqualTo("memberId", newCartItem.getMemberId())
                .andEqualTo("deleteStatus", newCartItem.getDeleteStatus())
                .andBetween("modifyDate", timestamp1, DateUtil.getDateNow());
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        if (cartItems.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 清空购物车
     * 查询锁定库存的商品
     *
     * @param cartItems
     * @return
     */
    public ResultData<List<CartItem>> cleanProductToCart(List<CartItem> cartItems, Integer status) {
        List<CartItem> newCarItems = new ArrayList<>();
        ResultData<List<CartItem>> resultData = new ResultData<List<CartItem>>();
        boolean b = true;
        // 查询出所有购物车商品，然后判断是否为失效购物车商品，这些不是失效的商品都是必要要操控库存的
        for (CartItem cartItem : cartItems) {
            // 查询该商品在购物车的信息，有多少件，各种信息，做数据更新
            cartItem = findByProuct(cartItem.getMemberId(), cartItem.getProductId(), cartItem.getDeleteStatus());
            // 不为空然后就先把要修改库存的数据放到list中返回
            if (null != cartItem) {
                if (isTimeout(cartItem)) {
                    // status 为1 就是清空购物车，为2 就是下单清购物车
                    if (status.equals(Integer.valueOf(StatusEnum.SUCCESS.getCode()))) {
                        // 为true代表锁定库存商品
                        newCarItems.add(cartItem);
                    }
                } else {
                    // 根据status来保证清空购物车永远不会添加，下单减库存进入该方法，添加未锁定库存商品，返回进行减少库存
                    if (status.equals(Integer.valueOf(StatusEnum.FAILED.getCode()))) {
                        newCarItems.add(cartItem);
                    }
                }
                // 逻辑删除购物车商品
                int i = cartItemMapper.updateByPrimaryKey(cartItem.setModifyDate(Timestamp.valueOf(DateUtil.getDateNow())).setDeleteStatus(2).setQuantity(0));
                // 设置一个为true的boolean，只有失败才会变为false
                if (i <= 0) {
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

    /**
     * 根据会员id 店铺id 不是删除的 查询购物车列表
     *
     * @param cartItem
     * @return
     */
    public List<CartItem> getCartItemList(CartItem cartItem) {
        Example example = new Example(CartItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("memberId", cartItem.getMemberId())
                .andEqualTo("shopId", cartItem.getShopId())
                .andEqualTo("deleteStatus", cartItem.getDeleteStatus());
        List<CartItem> cartItems = cartItemMapper.selectByExample(example);
        if (cartItems.size() > 0) {
            // 说明查询到数据
            return cartItems;
        }
        return null;
    }

}
