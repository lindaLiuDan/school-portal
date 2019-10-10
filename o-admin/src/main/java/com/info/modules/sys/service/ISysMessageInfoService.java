package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysMessageInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 系统消息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-16 15:01:31
 */
public interface ISysMessageInfoService extends IService<SysMessageInfoEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 系统消息表
     * @Date: 2019-07-16 15:01:31
     */
    /**
     * 功能描述: 系统消息表
     *
     * @Params:  * @param null
     * @Author:  Gaosx
     * @Date: 2019-07-16 15:01:31
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 查看详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/16 18:12
     * @Return:
     */
    SysMessageInfoEntity getMessageInfoById(Integer messageId);

}

