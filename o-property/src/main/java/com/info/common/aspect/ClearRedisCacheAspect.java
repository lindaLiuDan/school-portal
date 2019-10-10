package com.info.common.aspect;

import com.info.common.annotation.ClearRedisCache;
import com.info.redis.RedisUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 功能描述: ClearRedisCache注解切面类
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/4 14:17
 * @Return:
 */
@Aspect
@Component
public class ClearRedisCacheAspect {

    private static final Logger logger = LoggerFactory.getLogger(ClearRedisCacheAspect.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 功能描述:
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/3/28 14:31
     */
    @Pointcut("@annotation(com.info.common.annotation.ClearRedisCache)")
    public void clearRedisCachePointCut() {

    }

    /**
     * 功能描述: 之前要执行的切面
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/3/28 14:31
     */
    @After("clearRedisCachePointCut()")
    public void dataFilter(JoinPoint point) throws Throwable {
        logger.info("<---------------------开始执行删除操作-------------------->");
        final MethodSignature methodSignature = (MethodSignature) point.getSignature();
        final Method method = methodSignature.getMethod();

        final ClearRedisCache annotation = method.getAnnotation(ClearRedisCache.class);

        if (annotation == null) {
            return;
        }

        final String[] redisKeys = annotation.value();
        logger.info("RedisKeys:" + redisKeys);
        if (redisKeys.length == 0) {
            return;
        }

        for (String key : redisKeys) {
            logger.info("<**********循环删除中*********>");
            redisUtils.delete(key);
            logger.trace("Redis key {} has been deleted!", method.toString());
        }

    }

}
