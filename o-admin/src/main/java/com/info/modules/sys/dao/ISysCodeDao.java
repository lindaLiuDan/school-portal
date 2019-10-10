package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.SysCodeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-04-19 16:00:38
 */
public interface ISysCodeDao extends BaseMapper<SysCodeEntity> {

    SysCodeEntity findCode(@Param("mobile") String key, @Param("mobileCode") String value, @Param("date") Date date);
}
