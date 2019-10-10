package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingImgEntity;

import java.util.Map;

/**
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
public interface IFriendRingImgService extends IService<FriendRingImgEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈图片表
     * @Date: 2019-06-06 18:05:24
     */
    Integer queryPage(Map<String, Object> params);

}

