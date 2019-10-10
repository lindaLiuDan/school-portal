package com.info.modules.community.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommunityReleaseVo {


    /**
     * 社区电子放行单ID主键
     */
    private Integer slipId;

    /**
     * 业主会员姓名
     */
    private String userName;
    /**
     * 业主身份证号码
     */
    private String card;



    //小区名称
    private String infoName;
    /**
     * 楼层和房间号
     */
    private String room;
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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;

    //物业放行时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;

    private Integer identity;
}
