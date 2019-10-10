package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.modules.community.form.CommunityReleaseForm;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Date;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
public interface ICommunityReleaseSlipService extends IService<CommunityReleaseSlipEntity> {

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description:
     * @Date: 2019-06-25 18:19:11
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description 添加电子放行单
     * @Author LiuDan
     * @Date 2019/6/25 19:08
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage saveEntity(CommunityReleaseForm form);

    /**
     * @Description 根据电话号码和当前时间判断这个短话是否过期
     * @Author LiuDan
     * @Date 2019/7/7 15:24
     * @Param
     * @Return
     * @Exception
     */
    Boolean getEntityByMobile(String mobile, Date date);


    /**
     * @Description 查询详情
     * @Author LiuDan
     * @Date 2019/6/26 9:30
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage getDetail(Integer userId, Integer slipId);

    /**
     * @Description 申请记录
     * @Author LiuDan
     * @Date 2019/7/8 16:44
     * @Param
     * @Return
     * @Exception
     */
    PageUtils getApplyList(Map<String,Object> map);

    /**
     * @Description 放行记录
     * @Author LiuDan
     * @Date 2019/7/8 16:44
     * @Param
     * @Return
     * @Exception
     */
    PageUtils getPassList(Map<String,Object> map);

}

