package com.aaa.lee.app.mapper;

import com.aaa.lee.app.model.OrderItem;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface OrderItemMapper extends Mapper<OrderItem> {
}