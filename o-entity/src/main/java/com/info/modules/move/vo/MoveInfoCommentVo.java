package com.info.modules.move.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MoveInfoCommentVo implements Serializable {

    private static final long serialVersionUID = 1L;


    private Integer commentId;
    /**
     * 社区活动ID主键
     */
    private Integer moveId;
    /**
     * 会员ID主键
     */
    private Integer userId;
    /**
     * 留言内容
     */
    private String comment;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;

    private Integer sort;


    private String userName;

    private String img;
}
