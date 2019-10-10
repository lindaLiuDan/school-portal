package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.utils.PageUtils;
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
     * 功能描述: 社区朋友圈点赞表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 10:56
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 功能描述: 查看点赞人信息详情
     *
     * @Params:  * @param null 
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:16
     * @Return: 
     */
    FriendRingLikeEntity getFriendRingLikeInfo(Integer likeId);

    /**
     * 功能描述: 删除点赞信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:31
     * @Return:
     */
    ResultMessage delFriendRingLike(Integer likeId);

}

