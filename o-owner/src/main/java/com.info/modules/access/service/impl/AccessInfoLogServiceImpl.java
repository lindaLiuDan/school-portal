package com.info.modules.access.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.access.entity.AccessInfoLogEntity;
import com.info.modules.access.dao.IAccessInfoLogDao;
import com.info.modules.access.service.IAccessInfoLogService;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 门禁开锁信息记录表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-14 12:11:40
 */
@Service("accessInfoLogService")
public class AccessInfoLogServiceImpl extends ServiceImpl<IAccessInfoLogDao, AccessInfoLogEntity> implements IAccessInfoLogService {

    /**
     * 功能描述: 门禁开锁信息记录表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 14:00
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<AccessInfoLogEntity> page = this.page(
                new Query<AccessInfoLogEntity>().getPage(params),
                new QueryWrapper<AccessInfoLogEntity>()
                        .select("acc_id,info_id,user_id,is_status,creator_time,memo")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }



}
