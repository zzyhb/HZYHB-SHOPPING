package com.yhb.cms.controller;

import com.yhb.cms.exception.BizException;
import com.yhb.cms.exception.BizExceptionEnum;
import com.yhb.common.result.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: wb-yhb513235
 * @Date: 2019/3/18 17:46
 * @Description:
 */
@ControllerAdvice
@RestController
@Slf4j
public class BaseController {
    @ExceptionHandler(value = Exception.class)
    public BaseResult globalExceptionHandle(Exception exception) {
        log.error("发生异常:", exception);
        if (exception instanceof BizException){
            return BaseResult.setErrorResult(((BizException) exception).getBizExceptionEnum().getErrorId(),((BizException) exception).getBizExceptionEnum().getErrorMsg());
        }
        return null;
    }
}
