package com.info.modules.friend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.friend.entity.FriendRingEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
public interface IFriendRingService extends IService<FriendRingEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈信息表
     * @Date: 2019-06-06 18:05:30
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description 查询朋友圈详情
     * @Author LiuDan
     * @Date 2019/6/11 14:41
     * @Param
     * @Return
     * @Exception
     */
    FriendRingEntity getDetail(Integer ringId, Integer userId);

    /**
     * @Description 上传图片
     * @Author LiuDan
     * @Date 2019/6/22 14:48
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage upload(MultipartFile[] file);

    /**
     * 功能描述: 删除朋友圈根据ID
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 12:21
     * @Return:
     */
    ResultMessage delFriendRing(Integer ringId, Integer userId);
}

