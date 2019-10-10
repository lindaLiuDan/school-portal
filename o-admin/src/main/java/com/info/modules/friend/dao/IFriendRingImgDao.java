package com.info.modules.friend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.friend.entity.FriendRingImgEntity;

import java.util.List;

/**
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
public interface IFriendRingImgDao extends BaseMapper<FriendRingImgEntity> {

    /**
     * @Description 根据社区朋友圈id  查询图片
     * @Author LiuDan
     * @Date 2019/6/11 12:17
     * @Param
     * @Return
     * @Exception
     */
    List<FriendRingImgEntity> getImgByInfRingId(Integer ringId);

}
