package com.yhb.member.dao;

import com.yhb.member.entity.MemberInfo;
import com.yhb.member.entity.MemberInfoExample;
import com.yhb.member.vo.SimpleMemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "memberInfoMapper")
public interface MemberInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MemberInfo record);

    int insertSelective(MemberInfo record);

    List<MemberInfo> selectByExample(MemberInfoExample example);

    MemberInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MemberInfo record);

    int updateByPrimaryKey(MemberInfo record);

    List<SimpleMemberInfo> selectMemberSimpleInfoByExample();
}