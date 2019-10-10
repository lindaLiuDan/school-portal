package com.info.exception;

/**
 * 功能描述: 自定义异常
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 14:22
 * @Return:
 */
public class LiftException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 509;

    public LiftException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LiftException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public LiftException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public LiftException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
