package com.info.modules.lease.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.lease.entity.CommunityLeaseInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
public interface ICommunityLeaseInfoService extends IService<CommunityLeaseInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区租赁信息表
     * @Date: 2019-06-17 15:28:11
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 查看详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/18 9:14
     * @Return:
     */
    CommunityLeaseInfoEntity getLeaseInfoById(Integer leaseId);

}

