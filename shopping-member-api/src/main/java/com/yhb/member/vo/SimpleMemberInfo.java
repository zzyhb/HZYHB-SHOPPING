package com.yhb.member.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/7 19:35
 * @Description:
 */
@Getter
@Setter
@ToString
@Builder
public class SimpleMemberInfo implements Serializable {
    private Long id;

    private Date gmtCreate;

    private Date gmtModify;

    private String membercode;

    private String membername;

    private String memberphone;
}
