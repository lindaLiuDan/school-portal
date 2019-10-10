package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.DictEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 数据字典
 *
 * @author Gaosx
 */
public interface SysDictService extends IService<DictEntity> {

    /**
     * 功能描述: 分页查询字典信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 17:04
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);
}

