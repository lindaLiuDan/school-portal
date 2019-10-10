package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityPassInfoEntity;
import com.info.utils.ResultMessage;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
public interface ICommunityPassInfoService extends IService<CommunityPassInfoEntity> {

    /**
     * @Description 物业查询访客通行证的验证码是否可用
     * @Author LiuDan
     * @Date 2019/7/8 22:58
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage findCheckCode(String checkCode);
}