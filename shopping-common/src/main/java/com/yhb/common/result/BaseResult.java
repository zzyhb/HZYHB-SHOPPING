package com.yhb.common.result;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/8 09:39
 * @Description:
 */
@Getter
@Setter
@ToString
public class BaseResult implements Serializable {

    private Integer code;
    private Boolean success;
    private String message;
    private Integer size;
    private Object data;

    public BaseResult() {
        this.code = 200;
        this.success = true;
        this.message = "成功";
        this.data = null;
    }

    public BaseResult(BaseResult result) {
        this.success = result.getSuccess();
        this.code = result.getCode();
        this.message = result.getMessage();
        this.data = result.getData();
    }

    public static BaseResult setErrorResult(String errorMessage) {
        BaseResult baseResult = new BaseResult();
        baseResult.setSuccess(false);
        baseResult.setMessage(errorMessage);
        return baseResult;
    }

    public static BaseResult setErrorResult(Integer code,String errorMessage) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(code);
        baseResult.setSuccess(false);
        baseResult.setMessage(errorMessage);
        return baseResult;
    }

    public static BaseResult setSuccessResult(Object data) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(200);
        baseResult.setSuccess(true);
        baseResult.setData(data);
        return baseResult;
    }

    public static BaseResult setSuccessResultSize(Object data,Integer size) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(200);
        baseResult.setSuccess(true);
        baseResult.setData(data);
        baseResult.setSize(size);
        return baseResult;
    }

    public static BaseResult setSuccessMessage(String message) {
        BaseResult baseResult = new BaseResult();
        baseResult.setCode(200);
        baseResult.setSuccess(true);
        baseResult.setMessage(message);
        return baseResult;
    }
}
