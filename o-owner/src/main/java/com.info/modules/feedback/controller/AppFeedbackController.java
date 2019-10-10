package com.info.modules.feedback.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.feedback.entity.AppFeedbackEntity;
import com.info.modules.feedback.form.SaveAppFeedbackForm;
import com.info.modules.feedback.service.IAppFeedbackService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * app端意见反馈操作的集合
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-11-14 17:59:26
 */
@RestController
@RequestMapping("api/feedback")
public class AppFeedbackController extends AbstractController {

    /**
     * 功能描述: 意见反馈信息业务实现层
     *
     * @auther: 960889426@qq.com By User
     * @param:
     * @date: 2018/11/14 18:06
     */
    @Autowired
    private IAppFeedbackService appFeedbackService;


    /**
     * 分页查询我的列表信息
     *
     * @Author: Gaosx  960889426@qq.com
     * @Description: 列表查询
     * @Date: 2018-11-14 17:59:26
     */
    @RequestMapping("/list")
    @ApiOperation(value = "分页查询我的列表信息", response = AppFeedbackEntity.class)
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = appFeedbackService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }


    /**
     * @param
     * @return
     * @description: 添加反馈信息
     * @author liudan
     * @date 2019/3/19 14:26
     */
    @Login
    @RequestMapping("/addFeedback")
    public ResultMessage addFeedback(SaveAppFeedbackForm feedbackEntity) {
        ValidatorUtils.validateEntity(feedbackEntity, 0, AddGroup.class);
        return appFeedbackService.save(feedbackEntity);
    }


}
