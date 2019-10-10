package com.info.modules.area.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.area.entity.OpenCityEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 开放城市区域信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-12 19:31:54
 */
public interface IOpenCityService extends IService<OpenCityEntity> {

    /**
     * @Author: Gaosx
     * @Description: 开放城市区域信息表
     * @Date: 2019-06-12 19:31:54
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页的查询所有开放城市
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:40
     * @Return:
     */
    List<OpenCityEntity> all(Map<String, Object> params);
}

