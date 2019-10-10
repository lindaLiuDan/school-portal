package com.info.modules.friend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.modules.friend.vo.UserRingLikeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
public interface IFriendRingLikeDao extends BaseMapper<FriendRingLikeEntity> {
    /**
     * @Description 根据朋友圈id 查询点赞人信息
     * @Author LiuDan
     * @Date 2019/6/11 12:35
     * @Param
     * @Return
     * @Exception
     */
    List<UserRingLikeVo> getUserById(@Param("ringId") Integer ringId);


    /**
     * @Description 查询该用户是否已经点赞过
     * @Author LiuDan
     * @Date 2019/6/11 15:32
     * @Param
     * @Return
     * @Exception
     */
    FriendRingLikeEntity getEntityByUId(@Param("userId") Integer userId, @Param("ringId") Integer ringId);

}
