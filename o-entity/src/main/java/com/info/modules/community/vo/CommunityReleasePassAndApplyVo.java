package com.info.modules.community.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommunityReleasePassAndApplyVo {


    /**
     * 社区电子放行单ID主键
     */
    private Integer slipId;

    /**
     * 放行单码
     */
    private String slipCode;


    //物业放行时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date openTime;

    /**
     * 放行申请原因
     */
    private String slipCause;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
}
