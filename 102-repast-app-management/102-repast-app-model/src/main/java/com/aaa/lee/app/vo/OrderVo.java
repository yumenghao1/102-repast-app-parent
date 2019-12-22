package com.aaa.lee.app.vo;

import com.aaa.lee.app.model.Order;
import com.aaa.lee.app.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class OrderVo extends Order{
    List<OrderItem> orderItemList;
}
