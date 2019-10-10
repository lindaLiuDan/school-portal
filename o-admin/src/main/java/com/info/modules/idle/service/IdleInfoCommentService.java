package com.info.modules.idle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.idle.entity.IdleInfoCommentEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * 闲置交易评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
public interface IdleInfoCommentService extends IService<IdleInfoCommentEntity> {

    /**
     * 功能描述: 闲置交易评论表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/22 15:52
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 查询闲置交易评论集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 20:33
     * @Return:
     */
    List<IdleInfoCommentEntity> commentList(Integer idleId);

    /**
     * 功能描述: 闲置交易评论删除接口--被评论不可删除
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 19:46
     * @Return:
     */
    ResultMessage delIdleComment(Integer commentId, Integer idleId);


}

