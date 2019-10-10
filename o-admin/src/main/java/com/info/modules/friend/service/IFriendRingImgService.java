package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingImgEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.List;
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
     * 功能描述: 社区朋友圈图片表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:00
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据ID删除图片的方法
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:34
     * @Return:
     */
    ResultMessage delFriendRingImg(Integer imgId,Integer ringId);

    /**
     * 功能描述: 根据ID查询所有图片的集合
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 12:13
     * @Return:
     */
    List<FriendRingImgEntity> getFriendRingImg(Integer ringId);

    /**
     * 功能描述: 根据ringId删除图片集合
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:34
     * @Return:
     */
    ResultMessage delFriendRingImgList(Integer ringId);

}

