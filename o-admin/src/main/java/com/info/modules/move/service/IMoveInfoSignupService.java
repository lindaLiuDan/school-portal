package com.info.modules.move.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.move.entity.MoveInfoSignupEntity;
import com.info.modules.move.form.MoveInfoSignForm;
import com.info.utils.PageUtils;
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
     * 功能描述: 社区活动报名表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:53
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 活动报名信息详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:55
     * @Return:
     */
    MoveInfoSignupEntity getMoveInfoSignup(Integer upId);

}

