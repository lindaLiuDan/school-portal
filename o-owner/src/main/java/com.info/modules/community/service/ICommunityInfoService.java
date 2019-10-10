package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 社区小区信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
public interface ICommunityInfoService extends IService<CommunityInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区小区信息表
     * @Date: 2019-06-11 15:08:22
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页获取所有的信息列表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/11 15:44
     * @Return:
     */
    List<CommunityInfoEntity> all(Map<String, Object> params);

    /**
     * 功能描述: 获取社区详情信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 17:45
     * @Return:
     */
    CommunityInfoEntity getCommunityInfoById(Integer infoId);

}

