package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysLogEntity;
import com.info.utils.PageUtils;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Gaosx
 */
public interface SysLogService extends IService<SysLogEntity> {

    /**
     * 功能描述: 系统日志
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 16:46
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}
