package com.info.modules.access.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.access.entity.AccessInfoLogEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 门禁开锁信息记录表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-14 12:11:40
 */
public interface IAccessInfoLogService extends IService<AccessInfoLogEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 门禁开锁信息记录表
     * @Date: 2019-06-14 12:11:40
     */
    PageUtils queryPage(Map<String, Object> params);

}

