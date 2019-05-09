package com.yhb.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private String isDeleted;

    private String code;

    private String nickName;

    private String mobile;

    private String email;

    private String imgPath;

    private String password;

    private Integer sex;

    private Double balance;

    private Double frozenbalance;

    private Double jfvalue;

    private Double frozenjfvalue;

    private String province;

    private String city;

    private String area;

    private String address;

    private String paypassword;

    private String realName;

    private String remark;

    private String level;

    private Integer type;

    private String recommendMobile;

    private String cardNumber;
}