package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysRoleEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 功能描述: 角色
 *
 * @Params:  * @param null
 * @Author:  Gaosx By User
 * @Date: 2019/7/22 17:02
 * @Return:
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	/**
	 * 功能描述: 分页查询角色信息
	 *
	 * @Params:  * @param null
	 * @Author:  Gaosx By User
	 * @Date: 2019/7/22 17:02
	 * @Return:
	 */
	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
