package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.ConfigEntity;
import org.apache.ibatis.annotations.Param;

/**
 * 系统配置信息
 *
 * @author 960889426
 * @email 960889426@qq.com
 * @date 2016年12月4日 下午6:46:16
 */
public interface ISysConfigDao extends BaseMapper<ConfigEntity> {

    /**
     * 根据key，查询value
     */
    ConfigEntity queryByKey(String paramKey);

    /**
     * 根据key，更新value
     */
    int updateValueByKey(@Param("paramKey") String paramKey, @Param("paramValue") String paramValue);

}
