package com.info.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.oss.dao.SysOssDao;
import com.info.modules.oss.entity.SysOssEntity;
import com.info.modules.oss.service.SysOssService;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/26 11:19
 * @Return:
 */
@Service("sysOssService")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = this.page(
                new Query<SysOssEntity>().getPage(params)
        );
        return new PageUtils(page);
    }

}
