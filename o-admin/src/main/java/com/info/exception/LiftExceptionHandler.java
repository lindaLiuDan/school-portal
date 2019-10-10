package com.info.exception;

import com.info.utils.ResultMessage;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 功能描述: 项目异常全局处理器
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/15 14:56
 * @Return:
 */
@RestControllerAdvice
public class LiftExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 功能描述: 处理自定义异常
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/15 14:55
     * @Return:
     */
    @ExceptionHandler(LiftException.class)
    public ResultMessage handleRRException(LiftException e) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.put("code", e.getCode());
        resultMessage.put("msg", e.getMessage());
        return resultMessage;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResultMessage handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ResultMessage.error("数据库中已存在该记录");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResultMessage handleAuthorizationException(AuthorizationException e) {
        logger.error(e.getMessage(), e);
        return ResultMessage.error("没有权限，请联系管理员授权");
    }

    @ExceptionHandler(Exception.class)
    public ResultMessage handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultMessage.error();
    }
}
