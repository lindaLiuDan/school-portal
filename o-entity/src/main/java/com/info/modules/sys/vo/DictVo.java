package com.info.modules.sys.vo;

import java.io.Serializable;

/**
 * @Classname DictVo
 * @Description TODO 字典表返回字段
 * @Date 2019/3/13 10:52
 * @Created by yml
 */
public class DictVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 字典主键id
     */
    private Long id;
    /**
     * 字典名称
     */
    private String name;
    /**
     * 字典类型
     */
    private String type;
    /**
     * 字典码
     */

    private String code;
    /**
     * 字典值
     */
    private String value;
    /**
     * 备注
     */
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"id\":")
                .append(id);
        sb.append(",\"name\":\"")
                .append(name).append('\"');
        sb.append(",\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"code\":\"")
                .append(code).append('\"');
        sb.append(",\"value\":\"")
                .append(value).append('\"');
        sb.append(",\"remark\":\"")
                .append(remark).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
