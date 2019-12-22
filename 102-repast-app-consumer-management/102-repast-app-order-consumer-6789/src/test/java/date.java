import com.aaa.lee.app.model.CartItem;
import com.aaa.lee.app.utils.JSONUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class date {
    public static void main(String[] args) {
//        Date date = new Date();
//        long time = date.getTime();
//        Timestamp timestamp1 = new Timestamp(time);
//        System.out.println(timestamp1);
//        Long time1 = time - 600000;
//        Timestamp timestamp = new Timestamp(time1);
//        System.out.println(timestamp);
//
        List<CartItem> cartItems = new ArrayList<>();
        CartItem cartItem = new CartItem().setDeleteStatus(1).setMemberId(1L).setProductId(1L);
        CartItem cartItem1 = new CartItem().setDeleteStatus(1).setMemberId(1L).setProductId(2L);
        CartItem cartItem2 = new CartItem().setDeleteStatus(1).setMemberId(1L).setProductId(3L);
        cartItems.add(cartItem);
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        String s = JSONUtil.toJsonString(cartItems);
        System.out.println(s);

    }
}
