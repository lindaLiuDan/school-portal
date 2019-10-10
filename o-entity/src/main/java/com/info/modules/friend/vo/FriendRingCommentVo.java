package com.info.modules.friend.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FriendRingCommentVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer commentId;
    /**
     * 社区朋友圈ID主键
     */
    private Integer ringId;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论人
     */
    private Integer userId;
    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 上级父类ID
     */
    private Integer parentId;

    //评价人姓名
    private String userName;

    //回复人
    private String replayName;

}
