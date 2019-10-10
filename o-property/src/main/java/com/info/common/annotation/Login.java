package com.info.common.annotation;

import java.lang.annotation.*;

/**
 * 登录效验
 *
 * @author Gaosx
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Login {
}
