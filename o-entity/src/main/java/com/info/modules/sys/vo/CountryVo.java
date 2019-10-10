package com.info.modules.sys.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname CountryVo
 * @Description TODO
 * @Date 2019/3/14 18:51
 * @Created by yml
 */
public class CountryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Integer aId;
    /**
     * 区域id
     */
    private String areaId;
    /**
     * 父id
     */
    private String parentId;
    /**
     * 名称
     */
    private String names;
    /**
     * 子集
     */
    private List<CountryVo> sysCountrys;

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public List<CountryVo> getSysCountrys() {
        return sysCountrys;
    }

    public void setSysCountrys(List<CountryVo> sysCountrys) {
        this.sysCountrys = sysCountrys;
    }
}
