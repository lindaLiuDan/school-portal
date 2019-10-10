package com.info.modules.job.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.info.modules.job.entity.ScheduleJobLogEntity;
import com.info.modules.job.service.ScheduleJobLogService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {

	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public ResultMessage list(@RequestParam Map<String, Object> params){
		PageUtils page = scheduleJobLogService.queryPage(params);
		
		return ResultMessage.ok().put("page", page);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public ResultMessage info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.getById(logId);
		
		return ResultMessage.ok().put("log", log);
	}
}
