package com.info.modules.move.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.move.entity.MoveInfoSignupEntity;
import com.info.modules.move.form.MoveInfoSignForm;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
public interface IMoveInfoSignupService extends IService<MoveInfoSignupEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区活动报名表
     * @Date: 2019-06-06 18:05:23
     */
    Integer queryPage(Map<String, Object> params);


    /**
     * @Description 活动报名
     * @Author LiuDan
     * @Date 2019/6/10 11:53
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage saveSign(MoveInfoSignForm form);


    /**
     * @Description 个人中心——查询我参加的活动
     * @Author LiuDan
     * @Date 2019/6/17 15:12
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage getMyJoinMove(Map<String, Object> params);
}

