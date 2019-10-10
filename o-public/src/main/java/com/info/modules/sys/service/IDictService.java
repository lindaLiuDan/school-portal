package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.DictEntity;
import com.info.modules.sys.vo.DictVo;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Gaosx By user
 * @since 3.1.0 2018-01-27
 */
public interface IDictService extends IService<DictEntity> {


    /**
     * 功能描述: 分页获取所有的字典项
     *
     * @auther: Gaosx By User
     * @param:
     * @date: 2019/3/7 16:34
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @param key 字典表对应类型 , redisKey redis存的键
     * @return list字典集合
     * @description: 根据字典type 查询字典数据
     * @author Gaosx By User
     * @date 2019/3/12 17:03
     */
    List<DictVo> findDicInfo(String key);

    /**
     * @param key 字典表对应类型 , code的值
     * @return
     * @description: 根据redis结果集查询数据value返回
     * @author Gaosx By User
     * @date 2019/3/12 17:52
     */
    String findDicValueCode(String key, String code);

}

