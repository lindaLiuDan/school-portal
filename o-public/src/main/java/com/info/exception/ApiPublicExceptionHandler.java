package com.info.exception;

import com.info.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 * @author Gaosx
 */
@RestControllerAdvice
public class ApiPublicExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 功能描述: 处理自定义异常
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/4 14:13
     * @Return:
     */
    @ExceptionHandler(LiftException.class)
    public ResultMessage handleRRException(LiftException e) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.put("code", e.getCode());
        resultMessage.put("msg", e.getMessage());
        return resultMessage;
    }

    /**
     * 功能描述: 数据库中已存在该记录
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/4 14:13
     * @Return:
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultMessage handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return ResultMessage.error("数据库中已存在该记录");
    }

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/4 14:13
     * @Return:
     */
    @ExceptionHandler(Exception.class)
    public ResultMessage handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return ResultMessage.error();
    }
}


