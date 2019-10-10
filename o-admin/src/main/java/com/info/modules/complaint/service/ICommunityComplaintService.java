package com.info.modules.complaint.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.complaint.entity.CommunityComplaintEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区投诉建议信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-17 14:31:58
 */
public interface ICommunityComplaintService extends IService<CommunityComplaintEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 社区投诉建议信息表
     * @Date: 2019-06-17 14:31:58
     */
    PageUtils queryPage(Map<String, Object> params);

}

