package com.info.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
public interface ICommunityMessageTypeService extends IService<CommunityMessageTypeEntity> {

    /**
     * 功能描述: 社区通告通知类型表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:18
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 修改社区通告通知类型
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:20
     * @Return:
     */
    ResultMessage updateCommunityMessageType(CommunityMessageTypeEntity communityMessageTypeEntity);

    /**
     * 功能描述: del社区通告通知类型
     *
     * @Params:  * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:20
     * @Return:
     */
    ResultMessage delCommunityMessageType(Integer typeId);

}

