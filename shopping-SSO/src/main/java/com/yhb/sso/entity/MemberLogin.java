package com.yhb.sso.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/22 14:15
 * @Description: 用户登录信息
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MemberLogin implements Serializable {
    /**
     * 账号、手机号、邮箱均可
     */
    private String memberIdCard;
    private String password;
}
