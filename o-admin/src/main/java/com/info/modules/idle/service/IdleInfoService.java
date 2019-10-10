package com.info.modules.idle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.idle.entity.IdleInfoEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
public interface IdleInfoService extends IService<IdleInfoEntity> {

    /**
     * 功能描述: 闲置交易信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 18:08
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 下架信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:58
     * @Return:
     */
    ResultMessage delIdleInfo(Integer leId);

    /**
     * 功能描述: 闲置交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/10 13:39
     * @Return:
     */
    IdleInfoEntity getIdleInfoById(Integer leId);
}

