package com.info.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.user.form.SendSMSForm;

/**
 * 功能描述: 发送验证码的dao层
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/12 11:08
 * @Return:
 */
public interface ISendDao extends BaseMapper<SendSMSForm> {
}
