package com.info.modules.packag.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.packag.entity.PackageEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区快递包裹信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-17 15:28:11
 */
public interface IPackageService extends IService<PackageEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 社区快递包裹信息表
     * @Date: 2019-06-17 15:28:11
     */
    PageUtils queryPage(Map<String, Object> params);

}

