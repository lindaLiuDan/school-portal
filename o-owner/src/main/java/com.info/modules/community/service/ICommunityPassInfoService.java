package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityPassInfoEntity;
import com.info.modules.community.form.CommunityPassInfoForm;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Date;
import java.util.Map;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
public interface ICommunityPassInfoService extends IService<CommunityPassInfoEntity> {

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 社区访客通行证信息表
     * @Date: 2019-06-25 14:38:33
     */
    PageUtils queryPage(Map<String, Object> params);


    /**
     * @Description 添加访客同行——app
     * @Author LiuDan
     * @Date 2019/6/25 14:52
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage savePassInfo(CommunityPassInfoForm form);


    /**
     * @Description 根据当前手机号码和当前时间判断此电话号码是否已经过期
     * @Author LiuDan
     * @Date 2019/7/7 14:28
     * @Param
     * @Return
     * @Exception
     */
    Boolean getEntityByMobile(String mobile, Date date);

}

