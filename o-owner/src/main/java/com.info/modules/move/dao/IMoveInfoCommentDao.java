package com.info.modules.move.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 社区活动留言表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Mapper
public interface IMoveInfoCommentDao extends BaseMapper<MoveInfoCommentEntity> {


    /**
     * @Description 查询列表
     * @Author LiuDan
     * @Date 2019/6/10 9:39
     * @Param
     * @Return
     * @Exception
     */
    List<MoveInfoCommentEntity> getPageList(Map<String,Object> map);

    /**
     * @Description 查询总条数
     * @Author LiuDan
     * @Date 2019/6/10 9:47
     * @Param
     * @Return
     * @Exception
     */
    Integer getPageTotal(Map<String,Object> map);
}
