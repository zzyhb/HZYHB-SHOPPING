package com.yhb.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yhb.member.dao.MemberInfoMapper;
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
    public List<SimpleMemberInfo> getAllMemberSimpleInfos(){
        return memberInfoMapper.selectMemberSimpleInfoByExample();
    }
}
