package com.yhb.sso.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yhb.common.result.BaseResult;
import com.yhb.redis.service.RedisService;
import com.yhb.sso.dao.MemberInfoMapper;
import com.yhb.sso.entity.MemberInfo;
import com.yhb.sso.entity.MemberInfoExample;
import com.yhb.sso.entity.MemberLogin;
import com.yhb.sso.service.AuthTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/22 14:10
 * @Description:
 */
@Component
@Service(version = "1.0.0")
public class AuthTokenServiceImpl implements AuthTokenService {
    @Autowired
    MemberInfoMapper memberInfoMapper;
    @Reference(version = "1.0.0")
    RedisService redisService;

    @Override
    public MemberInfo doLogin(MemberLogin memberLogin) {
        /**
         * 判断是邮箱登录还是手机号登录还是账号登录
         */
        //邮箱
        if (memberLogin.getMemberIdCard().indexOf("@") != -1) {
            MemberInfoExample memberInfoExample = new MemberInfoExample();
            memberInfoExample.createCriteria().andEmailEqualTo(memberLogin.getMemberIdCard()).andPasswordEqualTo(memberLogin.getPassword());
            List<MemberInfo> memberInfos = memberInfoMapper.selectByExample(memberInfoExample);
            if (CollectionUtils.isNotEmpty(memberInfos)) {
                return memberInfos.get(0);
            }
            //账号
        } else if (memberLogin.getMemberIdCard().indexOf("HZYHB_") != -1) {
            MemberInfoExample memberInfoExample = new MemberInfoExample();
            memberInfoExample.createCriteria().andCodeEqualTo(memberLogin.getMemberIdCard()).andPasswordEqualTo(memberLogin.getPassword());
            List<MemberInfo> memberInfos = memberInfoMapper.selectByExample(memberInfoExample);
            if (CollectionUtils.isNotEmpty(memberInfos)) {
                return memberInfos.get(0);
            }
            //手机号
        } else {
            MemberInfoExample memberInfoExample = new MemberInfoExample();
            memberInfoExample.createCriteria().andMobileEqualTo(memberLogin.getMemberIdCard()).andPasswordEqualTo(memberLogin.getPassword());
            List<MemberInfo> memberInfos = memberInfoMapper.selectByExample(memberInfoExample);
            if (CollectionUtils.isNotEmpty(memberInfos)) {
                return memberInfos.get(0);
            }
        }
        return null;
    }

    @Override
    public BaseResult isLogin(String token) {
        BaseResult baseResult = new BaseResult();
        if (redisService == null) {
            baseResult.setSuccess(false);
            baseResult.setMessage("redis服务获取失败");
            return baseResult;
        }
        Object redisValue = redisService.getRedisValue(token);
        if (redisValue != null) {
            baseResult.setSuccess(true);
            baseResult.setData(redisValue);
            return baseResult;
        } else {
            baseResult.setSuccess(false);
            baseResult.setMessage("未登录,需跳转至登录界面");
            return baseResult;
        }
    }
}
