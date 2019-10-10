package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
public interface IFriendRingLikeService extends IService<FriendRingLikeEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈点赞表
     * @Date: 2019-06-06 18:05:24
     */
    Integer queryPage(Map<String, Object> params);

    /**
     * @Description 朋友圈点赞
     * @Author LiuDan
     * @Date 2019/6/11 15:32
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage saveRingLike(FriendRingLikeEntity friendRingLike);

}

