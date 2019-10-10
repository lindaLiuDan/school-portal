package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysMenuEntity;

import java.util.List;

/**
 * 菜单管理
 *
 * @author Gaosx
 */
public interface SysMenuService extends IService<SysMenuEntity> {

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId   父菜单ID
	 * @param menuIdList 用户菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

	/**
	 * 根据父菜单，查询子菜单
	 *
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);

	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

	/**
	 * 获取用户菜单列表
	 */
	List<SysMenuEntity> getUserMenuList(Long userId);

	/**
	 * 删除
	 */
	void delete(Long menuId);

	/**
	 * 功能描述: 返回所有菜单的URL
	 *
	 * @Params:  * @param null
	 * @Author:  Gaosx 960889426@qq.com By User
	 * @Date: 2019/6/4 9:52
	 * @Return:
	 */
	List<SysMenuEntity> allMenuList(SysMenuEntity sysMenuEntity);
}
