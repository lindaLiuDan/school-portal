package com.info.modules.mobile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
public interface ICommunityMobileService extends IService<CommunityMobileEntity> {


    /**
     * 功能描述: 社区便民生活电话
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:43
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 修改
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:48
     * @Return:
     */
    ResultMessage updateCommunityMobile(CommunityMobileEntity communityMobileEntity);

    /**
     * 功能描述: 修改
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:48
     * @Return:
     */
    ResultMessage delCommunityMobile(Integer mobileId);

    /**
     * 功能描述: 获取详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 15:57
     * @Return:
     */
    CommunityMobileEntity getCommunityMobile(Integer mobileId);

}

