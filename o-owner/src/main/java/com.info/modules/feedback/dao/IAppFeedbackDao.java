package com.info.modules.feedback.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.feedback.entity.AppFeedbackEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-11-14 17:59:26
 */
public interface IAppFeedbackDao extends BaseMapper<AppFeedbackEntity> {

    /**
     * @param
     * @return
     * @description: 查询列表_后台
     * @author liudan
     * @date 2019/4/26 14:50
     */
    List<AppFeedbackEntity> findPageList(Map<String, Object> map);

    /**
     * @param
     * @return
     * @description: 查询总页数
     * @author liudan
     * @date 2019/4/26 14:52
     */
    Integer findPageTotal(Map<String, Object> map);

    /**
     * @description: 根据id查询
     * @param
     * @return
     * @author liudan
     * @date 2019/4/26 15:36
     */
    AppFeedbackEntity getById(@Param("feedblackId") Integer feedblackId);
}
