package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.form.SendSMSForm;
import com.info.utils.ResultMessage;

/**
 * 发送验证码公共方法
 *
 * @author 960889426
 * @email 960889426@qq.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface ISendService extends IService<SendSMSForm> {

    /**
     * @Description 发送验证码公共方法
     * @Author LiuDan
     * @Date 2019/7/8 10:17
     * @Param
     * @Return
     * @Exception
     */

    ResultMessage send(SendSMSForm form);
}
