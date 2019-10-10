package com.info.modules.move.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.vo.MoveVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
@Mapper
public interface IMoveInfoDao extends BaseMapper<MoveInfoEntity> {


    /**
     * @Description 查询所有活动列表
     * @Author LiuDan
     * @Date 2019/7/7 10:52
     * @Param
     * @Return
     * @Exception
     */
    List<MoveVo> getMoveInfoList(Map<String, Object> map);

    /**
     * @Description 查询所有活动列表_总页数
     * @Author LiuDan
     * @Date 2019/7/7 10:52
     * @Param
     * @Return
     * @Exception
     */
    Integer getMoveInfoTotal(Map<String, Object> map);


    /**
     * @Description 查询详情
     * @Author LiuDan
     * @Date 2019/6/10 8:18
     * @Param
     * @Return
     * @Exception
     */
    MoveVo getDetail(@Param("moveId") Integer moveId);

}
