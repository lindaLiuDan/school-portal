package com.info.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: Api接口返回数据的格式以及处理方法
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 8:21
 * @Return:
 */
public class ResultMessage extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /**
     * @Author: Gaosx
     * @Description: 该方法是成功之后返回的格式 1以及以上的数字表示是成功
     * 0以及以下数字表示失败 msg表示信息描述
     * @Date: 12:09 2018/8/24
     */
    public ResultMessage() {
        put("code", ConfigConstant.SUCCESS);
        put("msg", "success");
    }

    public static ResultMessage error() {
        return error(ConfigConstant.NET_ERROR, "网络异常，请重试!");
    }

    public static ResultMessage err() {
        return error(ConfigConstant.ERROR, "error");
    }

    public static ResultMessage error(String msg) {
        return error(ConfigConstant.NET_ERROR, msg);
    }

    public static ResultMessage error(int code, String msg) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.put("code", code);
        resultMessage.put("msg", msg);
        return resultMessage;
    }

    public static ResultMessage ok(Object object) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.put("result", object);
        return resultMessage;
    }

    public static ResultMessage ok(Map<String, Object> map) {
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.putAll(map);
        return resultMessage;
    }

    public static ResultMessage ok() {
        return new ResultMessage();
    }

    public static ResultMessage ok(Integer code, String message, Object object) {
        ResultMessage result = new ResultMessage();
        result.put("code", code);
        result.put("msg", message);
        result.put("result", object);
        return result;
    }

    /**
     * 功能描述: 返回true false的方法
     *
     * @auther: Gaosx  By User
     * @param:
     * @date: 2019/3/11 13:58
     */
    public static ResultMessage flag(Boolean flag, String message, Object... object) {
        ResultMessage result = new ResultMessage();
        result.put("code", flag);
        result.put("msg", message);
        return result;
    }

    @Override
    public ResultMessage put(String key, Object value) {
        super.put(key, value);
        return this;
    }


}
