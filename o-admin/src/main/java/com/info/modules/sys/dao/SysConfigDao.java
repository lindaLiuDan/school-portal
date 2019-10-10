package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 功能描述: 系统配置信息
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/26 11:50
 * @Return:
 */
public interface SysConfigDao extends BaseMapper<ConfigEntity> {

    /**
     * 功能描述: 根据key，查询value
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:50
     * @Return:
     */
    ConfigEntity queryByKey(String paramKey);

    /**
     * 功能描述: 根据key，更新value
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:50
     * @Return:
     */
    int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);

}
