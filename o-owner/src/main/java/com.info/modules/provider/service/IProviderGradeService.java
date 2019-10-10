package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.vo.ProviderGradeVO;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
public interface IProviderGradeService extends IService<ProviderGradeVO> {


    /**
     * 功能描述: 商家(店铺)等级表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:57
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 获取所有店铺等级集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:59
     * @Return:
     */
    List<ProviderGradeVO> getProviderGradeList(Map<String, Object> params);

    /**
     * 功能描述: 获取单个店铺等级详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 18:06
     * @Return:
     */
    ProviderGradeVO getProviderGradeById(Integer gradeId);

}

