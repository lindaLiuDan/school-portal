package com.info.string;

import java.util.UUID;


/**
 * @ProjectName: gs-info
 * @ClasssName: UUIDUtils
 * @Description:
 * @Author: Gaosx 960889426@qq.com By Email
 * @Date: 2018/10/23 15:38
 * @Version: 1.0.0
 **/
public class UUIDUtils {

    /**
     * @Author: Gaosx 960889426@qq.com
     * @Description: 生产36位UUID
     * @Date: 15:39 2018/10/23
     */
    public static String getUUID36() {
        return UUID.randomUUID().toString();
    }

    /**
     * @Author: Gaosx 960889426@qq.com
     * @Description: 生产32位UUID
     * @Date: 15:39 2018/10/23
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
