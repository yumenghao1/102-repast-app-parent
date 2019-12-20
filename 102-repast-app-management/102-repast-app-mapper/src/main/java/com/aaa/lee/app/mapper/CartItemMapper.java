package com.aaa.lee.app.mapper;

import com.aaa.lee.app.model.CartItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface CartItemMapper extends Mapper<CartItem> {
}