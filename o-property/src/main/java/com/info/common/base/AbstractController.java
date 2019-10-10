package com.info.common.base;

import com.info.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能描述: Controller公共组件
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 7:53
 * @Return:
 */
public abstract class AbstractController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected RedisUtils redisUtils;


}
