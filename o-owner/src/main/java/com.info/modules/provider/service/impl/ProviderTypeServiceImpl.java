package com.info.modules.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.provider.entity.ProviderTypeEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.provider.dao.IProviderTypeDao;
import com.info.modules.provider.service.IProviderTypeService;
import com.info.modules.provider.vo.ProviderTypeEntityVo;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Service("providerTypeService")
public class ProviderTypeServiceImpl extends ServiceImpl<IProviderTypeDao, ProviderTypeEntityVo> implements IProviderTypeService {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:22
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(ProviderTypeServiceImpl.class);


    @Autowired
    private ICrudRedisManager<ProviderTypeEntityVo> crudRedisManager;


    /**
     * 功能描述: 商家分类信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProviderTypeEntityVo> page = this.page(
                new Query<ProviderTypeEntityVo>().getPage(params),
                new QueryWrapper<ProviderTypeEntityVo>()
                        .select("type_id,type_name,parent_id")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页获取商家分类信息表--parent_id=0d的顶级父类
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:24
     * @Return:
     */
    @Override
    public List<ProviderTypeEntityVo> getProviderTypeList(Map<String, Object> params) {
        String parentId = (String) params.get("parentId");
        String typeString = crudRedisManager.hget(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_list, parentId, "查询商家类别信息集合,Redis异常,Exception{},异常信息为:");
        if (typeString == null) {
            List<ProviderTypeEntityVo> list = this.list(new QueryWrapper<ProviderTypeEntityVo>()
                    .eq("parent_id", parentId)
            );
            crudRedisManager.hset(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_list, parentId, JSON.toJSONString(list), "存储商家类别信息集合,Redis异常,Exception{},异常信息为:");
            return list;
        }
        return JSON.parseArray(typeString, ProviderTypeEntityVo.class);
    }

    /**
     * 功能描述: 获取单个分类信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:50
     * @Return:
     */
    @Override
    public ProviderTypeEntityVo getProviderTypeById(Integer typeId) {
        ProviderTypeEntityVo providerTypeEntity = crudRedisManager.hget(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_INFO, typeId.toString(), ProviderTypeEntityVo.class, "获取单个类别信息,Redis异常,Exception{},异常信息为:");
        if (providerTypeEntity == null) {
            providerTypeEntity = this.getById(typeId);
            crudRedisManager.hset(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_INFO, typeId.toString(), JSON.toJSONString(providerTypeEntity), "获取单个类别信息,Redis异常,Exception{},异常信息为:");
            return providerTypeEntity;
        }
        return providerTypeEntity;
    }

    /**
     * 功能描述: 查询所有分类信息一次性返回的是顶级父类和自己的子类
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 10:04
     * @Return:
     */
    @Override
    public List<ProviderTypeEntityVo> getTypeList(Map<String, Object> params) {
        String typeList = crudRedisManager.get(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_ALL, "获取整个类别信息,Redis异常,Exception{},异常信息为:");
        if (typeList == null) {
            String parentId = (String) params.get("parentId");
            if (parentId == null) {
                parentId = ConfigConstant.PARENT_ID.toString();
            }
            List<ProviderTypeEntityVo> list = this.getTypeListParent(parentId);
            list.stream().forEach(info -> {
                info.setProviderTypeList(this.getTypeListParent(info.getTypeId().toString()));
            });
            crudRedisManager.set(RedisKeyUtils.ProviderKeys.PROVIDER_TYPE_ALL, JSON.toJSONString(list), "获取整个类别信息,Redis异常,Exception{},异常信息为:");
            return list;
        }
        return JSON.parseArray(typeList, ProviderTypeEntityVo.class);
    }

    /**
     * 功能描述: 配合上面的方法使用
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 10:10
     * @Return:
     */
    private List<ProviderTypeEntityVo> getTypeListParent(String parentId) {
        return this.list(new QueryWrapper<ProviderTypeEntityVo>()
                .select("type_id,parent_id,type_name,logo")
                .eq("parent_id", parentId)
        );
    }

}
