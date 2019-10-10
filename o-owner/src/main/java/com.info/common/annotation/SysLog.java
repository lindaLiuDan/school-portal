package com.info.common.annotation;

import java.lang.annotation.*;

/**
 * 功能描述: 系统日志注解
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/4 14:16
 * @Return:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}
