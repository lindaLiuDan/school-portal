package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderTypeEntity;
import com.info.modules.provider.vo.ProviderTypeEntityVo;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
public interface IProviderTypeService extends IService<ProviderTypeEntityVo> {

    /**
     * 功能描述: 分页获取商家分类信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:24
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页获取商家分类信息表--parent_id=0d的顶级父类
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:24
     * @Return:
     */
    List<ProviderTypeEntityVo> getProviderTypeList(Map<String, Object> params);

    /**
     * 功能描述: 获取单个分类信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:50
     * @Return:
     */
    ProviderTypeEntityVo getProviderTypeById(Integer typeId);

    /**
     * 功能描述: 查询所有分类信息一次性返回的是顶级父类和自己的子类
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 10:04
     * @Return:
     */
    List<ProviderTypeEntityVo> getTypeList(Map<String, Object> params);
}

