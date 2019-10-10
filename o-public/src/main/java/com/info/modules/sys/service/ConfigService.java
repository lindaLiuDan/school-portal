package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.ConfigEntity;
import com.info.utils.ResultMessage;


/**
 * 系统配置信息
 *
 * @author 960889426
 * @email 960889426@qq.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface ConfigService extends IService<ConfigEntity> {

    /**
    * @Description 根据key，获取配置的value值
    * @Author  LiuDan
    * @Date   2019/6/24 14:43
    * @Param
    * @Return
    * @Exception
    *
    */
    String getValue(String key);

    /**
     * @Description安卓更新
     * @Author LiuDan
     * @Date 2019/6/24 14:34
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage apkUpdate();

}
