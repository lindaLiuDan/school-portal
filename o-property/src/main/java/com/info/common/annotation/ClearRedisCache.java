package com.info.common.annotation;

import java.lang.annotation.*;

/**
 * Redis缓存清除的触发注解,
 * <p>
 * 任何有该注解的方法执行之后, 都将清除指定的redis缓存
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/4 14:17
 * @Return:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ClearRedisCache {

    /**
     * 方法执行之后需要清除的redis key列表
     *
     * @return
     */
    String[] value() default {};
}
