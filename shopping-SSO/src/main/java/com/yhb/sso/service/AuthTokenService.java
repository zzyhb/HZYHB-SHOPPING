package com.yhb.sso.service;

import com.yhb.common.result.BaseResult;
import com.yhb.sso.entity.MemberInfo;
import com.yhb.sso.entity.MemberLogin;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:05
 * @Description:
 */
public interface AuthTokenService {
    MemberInfo doLogin(MemberLogin memberLogin);

    BaseResult isLogin(String token);
}
