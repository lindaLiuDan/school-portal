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
     * @Author: Gaosx
     * @Description: 社区活动留言表
     * @Date: 2019-06-06 18:05:24
     */
    PageUtils queryPage(Map<String, Object> params);

}

