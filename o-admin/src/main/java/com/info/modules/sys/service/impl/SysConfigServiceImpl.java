package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.info.exception.LiftException;
import com.info.modules.sys.dao.SysConfigDao;
import com.info.modules.sys.entity.ConfigEntity;
import com.info.modules.sys.service.SysConfigService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Map;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/26 11:20
 * @Return:
 */
@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, ConfigEntity> implements SysConfigService {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:46
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String paramKey = (String) params.get("paramKey");
        IPage<ConfigEntity> page = this.page(
                new Query<ConfigEntity>().getPage(params),
                new QueryWrapper<ConfigEntity>()
                        .like(StringUtils.isNotBlank(paramKey), "param_key", paramKey)
                        .eq("status", ConfigConstant.NOTDEL)
        );
        return new PageUtils(page);
    }

    @Override
    public void saveConfig(ConfigEntity config) {
        this.save(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ConfigEntity config) {
        this.updateById(config);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateValueByKey(String key, String value) {
        baseMapper.updateValueByKey(key, value);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            ConfigEntity config = this.getById(id);
        }
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public String getValue(String key) {
        ConfigEntity config = baseMapper.queryByKey(key);
        return config == null ? null : config.getParamValue();
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key);
        if (StringUtils.isNotBlank(value)) {
            return new Gson().fromJson(value, clazz);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new LiftException("获取参数失败");
        }
    }
}
