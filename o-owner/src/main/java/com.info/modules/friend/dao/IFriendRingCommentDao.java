package com.info.modules.friend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
public interface IFriendRingCommentDao extends BaseMapper<FriendRingCommentEntity> {

    /**
     * @Description 根据父 和朋友圈id查询出来所有评价总页数
     * @Author LiuDan
     * @Date 2019/6/12 14:32
     * @Param
     * @Return
     * @Exception
     */
    Integer findAllTotal(Map<String, Object> map);

    /**
     * @Description 根据父 和朋友圈id查询出来所有评价  分页
     * @Author LiuDan
     * @Date 2019/6/12 14:32
     * @Param
     * @Return
     * @Exception
     */
    List<FriendRingCommentEntity> findAllCommentByPId(Map<String, Object> map);


}

