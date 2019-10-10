package com.info.modules.feedback.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.feedback.entity.AppFeedbackEntity;
import com.info.modules.feedback.form.SaveAppFeedbackForm;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 功能描述:  平台反馈业务实现层
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/12 17:52
 */
public interface IAppFeedbackService extends IService<AppFeedbackEntity> {

    /**
     * 功能描述:  分页查询反馈业务实现层
     *
     * @Author: Gaosx  960889426@qq.com
     * @Description:
     * @Date: 2018-11-14 17:59:26
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @param
     * @return
     * @description: 根据id 查询详情
     * @author liudan
     * @date 2019/4/26 15:37
     */
    AppFeedbackEntity getById(Integer feedblackId);

    /**
     * app端 --- 存平台反馈
     *
     * @param form
     * @return
     */
    ResultMessage save(SaveAppFeedbackForm form);

}

