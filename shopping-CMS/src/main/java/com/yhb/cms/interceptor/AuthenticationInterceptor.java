package com.yhb.cms.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.cms.exception.BizException;
import com.yhb.cms.exception.BizExceptionEnum;
import com.yhb.common.constant.Constant;
import com.yhb.common.result.BaseResult;
import com.yhb.common.util.CookieUtil;
import com.yhb.common.util.JSONUtil;
import com.yhb.member.service.MemberInfoService;
import com.yhb.redis.service.RedisService;
import com.yhb.sso.service.AuthTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.yhb.common.constant.Constant.cookieName;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/4/3 15:21
 * @Description: 请求拦截器
 */
//@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {};

    @Reference(version = "1.0.0")
    private AuthTokenService authTokenService;
    @Reference(version = "1.0.0")
    private RedisService redisService;
    @Reference(version = "1.0.0")
    private MemberInfoService memberInfoService;
    /**
     *  在业务处理器处理请求之前被调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
        if (authTokenService == null || redisService == null || memberInfoService == null){
            throw new BizException(BizExceptionEnum.DUBBORPCERROR);
        }
        if (null == CookieUtil.getUid(request, cookieName)){
            return false;
        }else {
            BaseResult login = authTokenService.isLogin(CookieUtil.getUid(request, cookieName));
            if(!login.getSuccess()){
                return false;
            }else {
                return true;
            }
        }
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.info("请求方式:{},url:{},耗时:{}ms",httpServletRequest.getMethod(),httpServletRequest.getRequestURI(),(System.currentTimeMillis()-TIME_THREADLOCAL.get()));
    }
}
