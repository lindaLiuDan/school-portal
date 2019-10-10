package com.info.modules.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 社区物业报修类型信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-14 14:11:07
 */
public interface ICommunityRepairTypeService extends IService<CommunityRepairTypeEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 社区物业报修类型信息表
     * @Date: 2019-06-14 14:11:07
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页查询所有的
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 14:20
     * @Return:
     */
    List<CommunityRepairTypeEntity> all(Map<String, Object> params);
}

