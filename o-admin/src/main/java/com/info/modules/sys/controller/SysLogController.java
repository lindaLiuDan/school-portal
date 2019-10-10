package com.info.modules.sys.controller;

import com.info.modules.sys.service.SysLogService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 系统日志
 *
 * @author Gaosx
 */
@Controller
@RequestMapping("/sys/log")
public class SysLogController {

	@Autowired
	private SysLogService sysLogService;
	
	/**
	 * 列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:log:list")
	public ResultMessage list(@RequestParam Map<String, Object> params){
		PageUtils page = sysLogService.queryPage(params);
		return ResultMessage.ok().put("page", page);
	}
	
}
