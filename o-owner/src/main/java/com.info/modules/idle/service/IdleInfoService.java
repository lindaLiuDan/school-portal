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
     * @Author: Gaosx
     * @Description: 闲置交易信息表
     * @Date: 2019-06-07 17:17:37
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据用户ID主键查询用户发布的闲置交易信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 15:31
     * @Return:
     */
    PageUtils idleList(Map<String, Object> params);

    /**
     * 功能描述: 分页查询闲置信息list---暂时废弃
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 15:29
     * @Return:
     */
    PageUtils pageIdleInfo(Map<String, Object> params);

    /**
     * 功能描述: 闲置交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/10 13:39
     * @Return:
     */
    IdleInfoEntity loadById(Integer leId);

    /**
     * 功能描述: 用户发布交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:06
     * @Return:
     */
    ResultMessage saveIdleInfo(IdleInfoEntity idleInfoEntity);

    /**
     * 功能描述: 用户删除发布交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:08
     * @Return:
     */
    ResultMessage delIdleInfo(Integer leId);


}

