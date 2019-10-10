package com.info.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.oss.entity.SysOssEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 文件上传
 *
 * @author Gaosx
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);
}
