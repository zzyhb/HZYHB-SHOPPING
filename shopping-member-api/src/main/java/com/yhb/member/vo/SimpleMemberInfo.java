package com.yhb.member.vo;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:35
 * @Description:
 */
@Data
@Builder
public class SimpleMemberInfo implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private String code;

    private String name;

    private String mobile;
}
