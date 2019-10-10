package com.info.modules.packag.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区快递包裹信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@Data
public class PackageEntityForm extends BaseEntity {


    /**
     * 社区快递包裹信息ID主键
     */
    @TableId
    private Integer pakegeId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 业主ID主键
     */
    private Integer userId;
    /**
     * 快递公司ID主键
     */
    private Integer courierCompany;
    /**
     * 快递单号
     */
    private String pakegeNo;
    /**
     * 取件码
     */
    private String pakegeCode;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     *
     */
    private Integer editor;

}
