package com.yhb.cms.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/18 16:19
 * @Description:
 */
@Getter
@Setter
@ToString
public class BizException extends Exception implements Serializable {
    /*
     * 模版异常
     */
    private BizExceptionEnum bizExceptionEnum;

    public BizException(BizExceptionEnum bizExceptionEnum){
        this.bizExceptionEnum = bizExceptionEnum;
    }
}
