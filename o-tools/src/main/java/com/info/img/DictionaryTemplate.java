package com.info.img;

import java.io.File;

/**
 * 功能描述: 判断是操作系统方法类
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/13 19:22
 */
public class DictionaryTemplate {

    /**
     * 返回保存文件的系统父路径
     *
     * @return 父路径
     */
    public static String getFileParentPath() {
        if ("\\".equals(File.separator)) {
            return "D:/upload/";
        }
        return "/upload/";
    }

}
