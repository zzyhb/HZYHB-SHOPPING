package com.yhb.sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.common.constant.Constant;
import com.yhb.common.result.BaseResult;
import com.yhb.common.util.CookieUtil;
import com.yhb.common.util.JSONUtil;
import com.yhb.redis.service.RedisService;
import com.yhb.sso.entity.MemberInfo;
import com.yhb.sso.entity.MemberLogin;
import com.yhb.sso.service.AuthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/22 11:01
 * @Description: 单点登录验证
 */
@RestController
@RequestMapping("/authToken")
@Slf4j
public class AuthTokenController {
    private final String cookieName = Constant.cookieName;
    @Autowired
    private AuthTokenService authTokenService;
    @Reference(version = "1.0.0")
    private RedisService redisService;

    @PostMapping(value = "/doLogin.do")
    public BaseResult doLogin(@RequestParam(value = "memberIdCard") String memberIdCard,
                              @RequestParam(value = "password") String password,
                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult baseResult = new BaseResult();
        if (StringUtils.isBlank(memberIdCard) || StringUtils.isBlank(password)){
            baseResult.setSuccess(false);
            baseResult.setMessage("账号密码不能为空");
        }
        MemberLogin memberLogin = new MemberLogin(memberIdCard,password);
        MemberInfo memberInfo = authTokenService.doLogin(memberLogin);
        if (memberInfo != null) {
            if (redisService == null) {
                baseResult.setSuccess(false);
                baseResult.setMessage("redis服务异常,请联系管理员检查");
            }
            if (CookieUtil.getUid(request, cookieName) == null) {
                String token = UUID.randomUUID().toString().replace("-", "");
                CookieUtil.addCookie(response, cookieName, token, 1000);
                redisService.setRedisValue(token, JSONUtil.toJsonStringWithNull(memberInfo), 1000);
            } else {
                redisService.setRedisValue(CookieUtil.getUid(request, cookieName), JSONUtil.toJsonStringWithNull(memberInfo), 1000);
            }
            baseResult.setSuccess(true);
            baseResult.setData(JSONUtil.toJsonStringWithNull(memberInfo));
        } else {
            baseResult.setSuccess(false);
            baseResult.setMessage("账号或密码错误,登录失败");
        }
        return baseResult;
    }

    @PostMapping("/loginOut.do")
    public BaseResult loginOut(HttpServletRequest request, HttpServletResponse response) {
        BaseResult baseResult = new BaseResult();
        if (redisService == null) {
            baseResult.setSuccess(false);
            baseResult.setMessage("redis服务异常,请联系管理员检查 ");
        }
        if (CookieUtil.getUid(request, cookieName) == null || redisService.getRedisValue(CookieUtil.getUid(request, cookieName)) == null) {
            baseResult.setSuccess(false);
            baseResult.setMessage("账号未登录,无法进行退出操作");
        } else {
            CookieUtil.removeCookie(response,cookieName);
            redisService.removeRedis(CookieUtil.getUid(request, cookieName));
            baseResult.setSuccess(true);
            baseResult.setMessage("成功退出账号");
        }
        return baseResult;
    }
}
