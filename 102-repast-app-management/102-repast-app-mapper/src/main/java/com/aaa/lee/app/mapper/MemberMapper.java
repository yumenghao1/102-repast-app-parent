package com.aaa.lee.app.mapper;

import com.aaa.lee.app.model.Member;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
@Repository
public interface MemberMapper extends Mapper<Member> {
}