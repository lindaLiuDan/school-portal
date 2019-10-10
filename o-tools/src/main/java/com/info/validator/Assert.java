package com.info.validator;

import com.info.exception.LiftException;
import org.apache.commons.lang.StringUtils;

/**
 * 功能描述: 数据校验
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/10 14:08
 * @Return:
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new LiftException(message);
        }
    }

    /**
     * 功能描述: 判断对象是否为空，然后返回对应的code和msg
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/10 14:35
     * @Return:
     */
    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new LiftException(message);
        }
    }

    /**
     * 功能描述: 上面方法的扩展
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/10 14:35
     * @Return:
     */
    public static void isNull(Object object, String message, Integer code) {
        if (object == null) {
            throw new LiftException(message, code);
        }
    }

    /**
     * 功能描述: 
     *
     * @Params:  * @param null 
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 9:23
     * @Return: 
     */
    public static void assertTrue(final boolean condition, final String msg) {
        if (!condition) {
            throw new LiftException(msg, 0);
        }
    }


}
