package com.info.modules.repair.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.repair.entity.CommunityRepairEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区物业报修表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:06
 */
public interface ICommunityRepairService extends IService<CommunityRepairEntity> {

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 10:47
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: {repairId}详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 19:37
     * @Return:
     */
    CommunityRepairEntity getCommunityRepairById(Integer repairId);

    /**
     * 功能描述: 用户提交物业报修信息表单
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 13:44
     * @Return:
     */
    ResultMessage saveCommunityRepair(CommunityRepairEntity communityRepairEntity);

}

