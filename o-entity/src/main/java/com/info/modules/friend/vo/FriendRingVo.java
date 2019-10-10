package com.info.modules.friend.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能描述:
 *
 * @Params:  * @param null
 * @Author:  Gaosx By User
 * @Date: 2019/7/6 10:15
 * @Return:
 */
@Data
public class FriendRingVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区朋友圈ID主键
     */
    private Integer ringId;
    /**
     * 所在社区小区ID主键
     */
    private Integer infoId;
    /**
     * 发布人
     */
    private Integer userId;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;

    /**
     * 点赞数
     */
    private Integer likeNum;

    //发布人姓名
    private String userName;

    //发布人头像
    private String img;

    //图片
    private List<ImgRingVo> imgList;

    //点赞
    private List<UserRingLikeVo> userList;

    //是否已经点赞   1 已点赞  2 未点赞
    private Integer ringLikeIsOrNot;

}
