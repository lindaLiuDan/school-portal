package com.info.modules.move.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.move.entity.MoveInfoSignupEntity;
import com.info.modules.move.vo.MoveVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
@Mapper
public interface IMoveInfoSignupDao extends BaseMapper<MoveInfoSignupEntity> {

    /**
     * @Description 根据用户id 查询我参加的活动列表
     * @Author LiuDan
     * @Date 2019/6/17 15:33
     * @Param
     * @Return
     * @Exception
     */
    List<MoveVo> findMyJoinMoveList(Map<String, Object> map);


    /**
     * @Description 根据用户id 查询我参加的活动总页数
     * @Author LiuDan
     * @Date 2019/6/17 15:33
     * @Param
     * @Return
     * @Exception
     */
    Integer findMyJoinMoveTotal(Map<String, Object> map);

}
