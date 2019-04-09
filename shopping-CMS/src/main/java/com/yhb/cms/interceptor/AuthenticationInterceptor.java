package com.yhb.cms.interceptor;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yhb.cms.exception.BizException;
import com.yhb.cms.exception.BizExceptionEnum;
import com.yhb.common.constant.Constant;
import com.yhb.common.result.BaseResult;
import com.yhb.common.util.CookieUtil;
import com.yhb.redis.service.RedisService;
import com.yhb.sso.service.AuthTokenService;
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
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Reference(version = "1.0.0")
    private AuthTokenService authTokenService;
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
        System.out.println("AuthenticationInterceptor-----------------preHandle---------------");
        if (null == CookieUtil.getUid(request, cookieName)){
            response.sendRedirect("https://www.baidu.com/");
            return false;
        }else {
            if (authTokenService == null){
                throw new BizException(BizExceptionEnum.DUBBORPCERROR);
            }
            BaseResult login = authTokenService.isLogin(CookieUtil.getUid(request, cookieName));
            if(!login.getSuccess()){
                response.sendRedirect("https://www.baidu.com/");
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
        System.out.println("AuthenticationInterceptor-----------------postHandle---------------");
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
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("AuthenticationInterceptor-----------------afterCompletion---------------");
    }
}
