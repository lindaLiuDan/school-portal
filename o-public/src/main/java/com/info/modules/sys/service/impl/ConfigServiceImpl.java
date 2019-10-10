package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.CrudRedisManager;
import com.info.modules.sys.dao.ISysConfigDao;
import com.info.modules.sys.entity.ConfigEntity;
import com.info.modules.sys.service.ConfigService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: LiuDan
 * @Date: 2019/6/24 14:29
 * @Description:
 */
@Service("sysConfigService")
public class ConfigServiceImpl extends ServiceImpl<ISysConfigDao, ConfigEntity> implements ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private CrudRedisManager<ConfigEntity> crudRedisManager;

    /**
     * @Description 安卓更新
     * @Author LiuDan
     * @Date 2019/6/24 14:42
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage apkUpdate() {
        Map<String, Object> map = new HashMap<>();
        String version = getValue(ConfigConstant.SysConfig.APK_VERSION);
        version = StringUtils.isNotBlank(version) ? version : "";
        map.put("version", version);
        String url = getValue(ConfigConstant.SysConfig.APK_URL);
        url = StringUtils.isNotBlank(url) ? url : "";
        map.put("url", url);
        String describe = getValue(ConfigConstant.SysConfig.APK_DESCRIBE);
        describe = StringUtils.isNotBlank(describe) ? describe : "";
        map.put("describe", describe);
        return ResultMessage.ok(map);
    }


    /**
     * @Description 根据key，获取配置的value值
     * @Author LiuDan
     * @Date 2019/6/24 14:43
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public String getValue(String key) {
        String config = crudRedisManager.hget(RedisKeyUtils.sysConfig.SYS_CONFIG, key, "根据KEY获取配置文件中的基础配置信息失败,Exception{},异常信息为：");
        if (config != null) {
            return config;
        } else {
            ConfigEntity configEntity = baseMapper.queryByKey(key);
            crudRedisManager.hset(RedisKeyUtils.sysConfig.SYS_CONFIG, key, configEntity.getParamValue(), "存储配置文件的基础配置信息失败,Exception{},异常信息为：");
            return configEntity == null ? null : configEntity.getParamValue();
        }
    }
}
