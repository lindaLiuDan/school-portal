package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.sys.dao.SysDictDao;
import com.info.modules.sys.entity.DictEntity;
import com.info.modules.sys.service.SysDictService;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 功能描述: 字典信息
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/26 11:20
 * @Return:
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, DictEntity> implements SysDictService {


    /**
     * 功能描述: 分页查询字典信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 17:04
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String dictName = (String) params.get("dictName");
        IPage<DictEntity> page = this.page(
                new Query<DictEntity>().getPage(params),
                new QueryWrapper<DictEntity>()
                        .like(StringUtils.isNotBlank(dictName), "dict_name", dictName)
        );
        return new PageUtils(page);
    }

}
