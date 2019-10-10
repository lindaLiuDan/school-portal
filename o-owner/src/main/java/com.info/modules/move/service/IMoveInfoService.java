package com.info.modules.move.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.vo.MoveVo;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
public interface IMoveInfoService extends IService<MoveInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区活动信息表
     * @Date: 2019-06-06 18:05:29
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description活动详情
     * @Author LiuDan
     * @Date 2019/6/10 8:13
     * @Param
     * @Return
     * @Exception
     */
    MoveVo getDetail(Integer moveId);

}

