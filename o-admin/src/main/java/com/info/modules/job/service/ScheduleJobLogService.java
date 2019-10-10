package com.info.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.job.entity.ScheduleJobLogEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Gaosx
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
