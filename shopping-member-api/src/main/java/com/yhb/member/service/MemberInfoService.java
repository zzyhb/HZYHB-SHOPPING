package com.yhb.member.service;

import com.yhb.member.entity.MemberInfo;
import com.yhb.member.vo.SimpleMemberInfo;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:05
 * @Description:
 */
public interface MemberInfoService {
    List<SimpleMemberInfo> getAllMemberSimpleInfos();

    List<MemberInfo> getAllMemberInfos();

    SimpleMemberInfo getMemberInfoById(Long id);

    Integer insertMember(MemberInfo memberInfo);

    Integer updateMemberInfoById(MemberInfo memberInfo);

    Integer deleteMemberInfoById(Long id);
}
