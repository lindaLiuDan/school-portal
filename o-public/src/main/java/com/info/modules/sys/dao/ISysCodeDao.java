package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.CodeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 21:50:17
 */
public interface ISysCodeDao extends BaseMapper<CodeEntity> {
    /**
     * @Description 查询验证码
     * @Author LiuDan
     * @Date 2019/6/13 22:01
     * @Param
     * @Return
     * @Exception
     */
    CodeEntity findCode(@Param("mobile") String key, @Param("mobileCode") String value, @Param("date") Date date);

}
