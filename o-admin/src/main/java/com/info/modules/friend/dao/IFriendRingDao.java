package com.info.modules.friend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.friend.entity.FriendRingEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
public interface IFriendRingDao extends BaseMapper<FriendRingEntity> {


    /**
     * @Description 查询总页数
     * @Author LiuDan
     * @Date 2019/6/11 12:15
     * @Param
     * @Return
     * @Exception
     */
    List<FriendRingEntity> getPageList(Map<String, Object> map);


    /**
     * @Description查询总页数
     * @Author LiuDan
     * @Date 2019/6/11 12:15
     * @Param
     * @Return
     * @Exception
     */
    Integer getPageTotal(@Param("infoId") Integer infoId);


    /**
     * @Description 根据朋友圈id 查询详情
     * @Author LiuDan
     * @Date 2019/6/11 14:44
     * @Param
     * @Return
     * @Exception
     */
    FriendRingEntity getDeatil(@Param("ringId") Integer ringId);


    /**
     * @Description 增加点赞数量
     * @Author LiuDan
     * @Date 2019/6/11 15:45
     * @Param
     * @Return
     * @Exception
     */
    boolean updateAddLikeNum(@Param("ringId") Integer ringId);

    /**
     * @Description 减少点赞数量
     * @Author LiuDan
     * @Date 2019/6/11 15:45
     * @Param
     * @Return
     * @Exception
     */
    boolean updateReduceLikeNum(@Param("ringId") Integer ringId);

}
