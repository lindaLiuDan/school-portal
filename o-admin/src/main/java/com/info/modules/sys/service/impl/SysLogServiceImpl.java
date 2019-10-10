package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.info.modules.sys.dao.SysLogDao;
import com.info.modules.sys.entity.SysLogEntity;
import com.info.modules.sys.service.SysLogService;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/7 17:37
 * @Return:
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {


    /**
     * 功能描述: 系统日志
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 16:46
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        IPage<SysLogEntity> page = this.page(
                new Query<SysLogEntity>().getPage(params),
                new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key), "username", key)
        );
        return new PageUtils(page);
    }
}
