package com.yhb.cms.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/18 12:01
 * @Description:
 */
@Getter
public enum  BizExceptionEnum {
    SERVERERROR(0001,"serverError","服务器异常"),
    DUBBORPCERROR(0002,"dubboRpcError","RPC远程调用异常");

    private Integer errorId;
    private String errorCode;
    private String errorMsg;

    BizExceptionEnum(Integer errorId,String errorCode, String errorMsg) {
        this.errorId = errorId;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public static String getErrorMsg(Integer errorId) {
        for (BizExceptionEnum bizException : values()){
            if (bizException.getErrorId().equals(errorId)){
                return bizException.getErrorMsg();
            }
        }
        return null;
    }

    public static String getErrorMsg(String errorCode) {
        for (BizExceptionEnum bizException : values()){
            if (bizException.getErrorCode().equals(errorCode)){
                return bizException.getErrorMsg();
            }
        }
        return null;
    }
}
