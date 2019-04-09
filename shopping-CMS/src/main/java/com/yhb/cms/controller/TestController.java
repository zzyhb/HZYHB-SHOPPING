package com.yhb.cms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.cms.exception.BizException;
import com.yhb.cms.exception.BizExceptionEnum;
import com.yhb.common.result.BaseResult;
import com.yhb.member.entity.MemberInfo;
import com.yhb.member.service.MemberInfoService;
import com.yhb.member.vo.SimpleMemberInfo;
import com.yhb.redis.service.RedisService;
import com.yhb.redis.util.RedisUtil;
import com.yhb.sso.service.AuthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:28
 * @Description:
 */
@RestController
@Slf4j
public class TestController{
    @Reference(version = "1.0.0")
    private MemberInfoService memberInfoService;
    @Reference(version = "1.0.0")
    private RedisService redisService;

    @RequestMapping("/test.do")
    public BaseResult test() throws BizException{
        if (memberInfoService == null || redisService == null){
            throw new BizException(BizExceptionEnum.DUBBORPCERROR);
        }
        BaseResult baseResult = new BaseResult();
        List<SimpleMemberInfo> allMemberInfos = memberInfoService.getAllMemberSimpleInfos();
        redisService.setRedisValue("allMemberInfos",allMemberInfos,1000);
        baseResult.setData(redisService.getRedisValue("allMemberInfos"));
        return baseResult;
    }
}
