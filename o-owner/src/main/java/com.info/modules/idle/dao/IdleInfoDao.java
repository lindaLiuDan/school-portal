package com.info.modules.idle.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.idle.entity.IdleInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
public interface IdleInfoDao extends BaseMapper<IdleInfoEntity> {


    /**
     * 功能描述: 分页查询闲置信息list
     *
     * @Params:  * @param null
     * @Author:  Gaosx  By User
     * @Date: 2019/6/8 15:29
     * @Return:
     */
    List<IdleInfoEntity> pageIdleInfo(Map<String, Object> params);

    /**
     * 功能描述: 对应上面的查询行数
     *
     * @Params:  * @param null
     * @Author:  Gaosx  By User
     * @Date: 2019/6/8 16:05
     * @Return:
     */
    int pageIdleInfoCount(Map<String, Object> params);

}
