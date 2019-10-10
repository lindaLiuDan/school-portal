package com.info.modules.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
@Data
@TableName("community_release_slip")
public class CommunityReleaseSlipEntity extends BaseEntity {


    /**
     * 社区电子放行单ID主键
     */
    @TableId
    private Integer slipId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 会员业主信息ID主键
     */
    private Integer userId;
    /**
     * 业主会员姓名
     */
    private String userName;
    /**
     * 业主身份证号码
     */
    private String card;

    /**
     * 楼层和房间号
     */
    private Integer buildId;
    /**
     * 放行开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 放行结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 放行申请原因
     */
    private String slipCause;
    /**
     * 物业审核结果
     */
    private String slipResult;
    /**
     * 审核结果(数据字典) 0 审核拒绝 1 审核通过
     */
    private Integer checkResult;
    /**
     * 放行单码
     */
    private String slipCode;
    /**
     * 备注
     */
    private String meomo;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 物业放行时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;

    /**
     * 放行人
     */
    private Integer editor;


    private String mobile;

}
