package com.info.modules.access.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.modules.access.dao.IAccessInfoLogDao;
import com.info.modules.access.entity.AccessInfoLogEntity;
import com.info.modules.access.service.IAccessInfoLogService;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

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
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 12:10
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        Assert.isNull(userId, "用户ID不能为空", ConfigConstant.ERROR);
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<AccessInfoLogEntity> page = this.page(
                new Query<AccessInfoLogEntity>().getPage(params),
                new QueryWrapper<AccessInfoLogEntity>()
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
