package com.info.modules.move.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区活动留言表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
public interface IMoveInfoCommentService extends IService<MoveInfoCommentEntity> {

    /**
     * 功能描述: 社区活动留言表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 15:02
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

