package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.DictEntity;
import com.info.modules.sys.vo.DictVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据字典
 *
 * @author Gaosx 960889426@qq.com
 * @since 3.1.0 2018-01-27
 */
public interface IDictInfoDao extends BaseMapper<DictEntity> {

    /**
     * @param key 对应数据库 type 字段
     * @return
     * @description: 根据字段type 查询字典集合
     * @author Gaosx By User
     * @date 2019/06/25 17:43
     */
    List<DictVo> findDisByType(@Param("key") String key);

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 14:23
     * @Return:
     */
    String findDisByTypeAndCode(@Param("key") String key, @Param("code") Integer code);
}
