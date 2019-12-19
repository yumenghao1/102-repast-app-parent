package com.aaa.lee.app.service;

import com.aaa.lee.app.base.BaseService;
import com.aaa.lee.app.mapper.MemberMapper;
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
public class MemberService extends BaseService<Member> {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Mapper<Member> getMapper() {
        return memberMapper;
    }

    /**
     * @author Seven Lee
     * @description
     *      执行登录操作
     * @param [member]
     * @date 2019/12/19
     * @return java.lang.Boolean
     * @throws 
    **/
    public Boolean doLogin(Member member) {
        if(StringUtil.isNotEmpty(member.getOpenId())) {
            try {
                // 随机token
                String token = IDUtil.getUUID() + member.getOpenId();
                member.setToken(token);
                Integer saveResult = super.save(member);
                if(saveResult > 0) {
                    // 说明添加成功
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
