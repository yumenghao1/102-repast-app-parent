package com.aaa.lee.app.fallback;

import com.aaa.lee.app.api.IRepastService;
import com.aaa.lee.app.base.ResultData;
import com.aaa.lee.app.model.Coupon;
import com.aaa.lee.app.model.Member;
import com.aaa.lee.app.model.MemberReceiveAddress;
import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.utils.DateUtil;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Company AAA软件教育
 * @Author Seven Lee
 * @Date Create in 2019/12/19 14:57
 * @Description
 **/
@Component
public class RepastFallback implements FallbackFactory<IRepastService> {

    @Override
    public IRepastService create(Throwable throwable) {
        IRepastService repastService = new IRepastService() {

            @Override
            public Boolean doLogin(Member member) {
                System.out.println("测试登录熔断数据");
                return false;
            }

            @Override
            public ResultData getAdrByMemberId(String token, Long id) {
                List<MemberReceiveAddress> memberReceiveAddresses = new ArrayList<>();
                MemberReceiveAddress memberReceiveAddress = new MemberReceiveAddress()
                        .setMemberId(1L)
                        .setName("于梦豪")
                        .setPhoneNumber("18595917116")
                        .setPostCode("50000")
                        .setProvince("河南省")
                        .setCity("郑州市")
                        .setRegion("金水区")
                        .setDetailAddress("aaa教育")
                        .setDefaultStatus(0);
                MemberReceiveAddress memberReceiveAddress1 = new MemberReceiveAddress()
                        .setMemberId(1L)
                        .setName("于梦豪")
                        .setPhoneNumber("18595917116")
                        .setPostCode("50000")
                        .setProvince("河南省")
                        .setCity("郑州市")
                        .setRegion("金水区")
                        .setDetailAddress("bbb教育")
                        .setDefaultStatus(1);
                MemberReceiveAddress memberReceiveAddress2 = new MemberReceiveAddress()
                        .setMemberId(1L)
                        .setName("于梦豪")
                        .setPhoneNumber("18595917116")
                        .setPostCode("50000")
                        .setProvince("河南省")
                        .setCity("郑州市")
                        .setRegion("金水区")
                        .setDetailAddress("ccc教育")
                        .setDefaultStatus(1);
                memberReceiveAddresses.add(memberReceiveAddress);
                memberReceiveAddresses.add(memberReceiveAddress1);
                memberReceiveAddresses.add(memberReceiveAddress2);
                return new ResultData().setCode("200").setData(memberReceiveAddresses).setMsg("查询成功");
            }

            @Override
            public ResultData<List<Coupon>> getCouponByMemberId(String token, Long memberId) {
                List<Coupon> coupons = new ArrayList<>();

                coupons.add(new Coupon().setName("5元优惠券")
                        .setPlatform(2)
                        .setAmount(BigDecimal.valueOf(5))
                        .setMinPoint(BigDecimal.valueOf(50))
                        .setStartTime(Timestamp.valueOf(DateUtil.getDateNow()))
                        .setEndTime(new Date(System.currentTimeMillis() + 6000000))
                        .setUseType(0)
                        .setNote("我日你mmp"));
                return new ResultData().setCode("200").setMsg("获取成功").setData(coupons);
            }

            @Override
            public ResultData<Coupon> selectCouponById(String token, Integer couponId) {
                return new ResultData<Coupon>().setData(new Coupon().setId(1L).setMinPoint(BigDecimal.valueOf(20)).setAmount(BigDecimal.valueOf(5))).setCode(LoginStatus.LOGIN_SUCCESS.getCode());
            }
        };
        return repastService;
    }
}
