package com.aaa.lee.app.vo;

import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.MemberReceiveAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TakeOutVo implements Serializable {
    private List<CartItem> cartItemList;
    private List<MemberReceiveAddress> memberReceiveAddressList;
    private List<Coupon> couponList;
    private Integer orderType;
}
