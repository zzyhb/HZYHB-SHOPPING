package com.yhb.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhb.member.dao.MemberInfoMapper;
import com.yhb.member.entity.MemberInfo;
import com.yhb.member.entity.MemberInfoExample;
import com.yhb.member.service.MemberInfoService;
import com.yhb.member.vo.SimpleMemberInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:06
 * @Description:
 */
@Component
@Service(version = "1.0.0")
public class MemberInfoServiceImpl implements MemberInfoService {
    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Override
    public List<SimpleMemberInfo> getAllMemberSimpleInfos() {
        return memberInfoMapper.selectMemberSimpleInfoByExample();
    }

    @Override
    public List<MemberInfo> getAllMemberInfos() {
        MemberInfoExample memberInfoExample = new MemberInfoExample();
        memberInfoExample.createCriteria().andIsDeletedEqualTo("0");
        return memberInfoMapper.selectByExample(memberInfoExample);
    }

    @Override
    public SimpleMemberInfo getMemberInfoById(Long id) {
        MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(id);
        SimpleMemberInfo simpleMemberInfo = transferMemberInfo2SimpleMemberInfo(memberInfo);
        return simpleMemberInfo;
    }

    @Override
    public Integer insertMember(MemberInfo memberInfo) {
        return memberInfoMapper.insertSelective(memberInfo);
    }

    @Override
    public Integer updateMemberInfoById(MemberInfo memberInfo) {
        return memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
    }

    @Override
    public Integer deleteMemberInfoById(Long id) {
        MemberInfo memberInfo = MemberInfo.builder()
                .id(id)
                .isDeleted("1").build();
        return memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
    }

    public SimpleMemberInfo transferMemberInfo2SimpleMemberInfo(MemberInfo memberInfo) {
        SimpleMemberInfo simpleMemberInfo = SimpleMemberInfo.builder()
                .id(memberInfo.getId())
                .gmtCreate(memberInfo.getGmtCreate())
                .gmtModify(memberInfo.getGmtModify())
                .code(memberInfo.getCode())
                .name(memberInfo.getNickName() != null ? memberInfo.getNickName() : memberInfo.getRealName())
                .mobile(memberInfo.getMobile()).build();
        return simpleMemberInfo;
    }
}
