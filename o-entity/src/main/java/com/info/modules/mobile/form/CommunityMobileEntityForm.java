package com.info.modules.mobile.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
@Data
public class CommunityMobileEntityForm extends BaseEntity {


    /**
     * 社区便民电话ID主键
     */
    @TableId
    private Integer mobileId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 社区便民服务项目
     */
    private String project;
    /**
     * 社区便民服务项目电话
     */
    private String mobile;
    /**
     * 备注
     */
    private String memo;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 排序数
     */
    private Integer sort;


}
