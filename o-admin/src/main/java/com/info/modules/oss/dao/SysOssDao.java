package com.info.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 * @author Gaosx
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
