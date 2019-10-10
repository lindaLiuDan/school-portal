package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import com.info.utils.PageUtils;

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
     * @Author: Gaosx
     * @Description: 社区朋友圈评论表
     * @Date: 2019-06-06 18:05:25
     */
    PageUtils queryPage(Map<String, Object> params);

}

