package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.utils.ResultMessage;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
public interface ICommunityReleaseSlipService extends IService<CommunityReleaseSlipEntity> {

    /**
     * @Description 物业查询电子放行单的验证码是否可用
     * @Author LiuDan
     * @Date 2019/7/8 22:58
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage getCheckCode(String checkCode);
}