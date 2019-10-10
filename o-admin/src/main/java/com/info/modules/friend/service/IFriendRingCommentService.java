package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
public interface IFriendRingCommentService extends IService<FriendRingCommentEntity> {


    /**
     * 功能描述: 社区朋友圈评论表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:01
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 删除评论信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:53
     * @Return:
     */
    ResultMessage delFriendRingComment(Integer commentId);

    /**
     * 功能描述: 查看评论信息详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:58
     * @Return:
     */
    FriendRingCommentEntity getFriendRingComment(Integer commentId);

}

