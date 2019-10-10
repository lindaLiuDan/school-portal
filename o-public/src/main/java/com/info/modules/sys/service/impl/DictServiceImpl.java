package com.info.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.sys.dao.IDictInfoDao;
import com.info.modules.sys.entity.DictEntity;
import com.info.modules.sys.service.IDictService;
import com.info.modules.sys.vo.DictVo;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 数据字典
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param: Gaosx By user
 * @date: 2019/3/7 16:10
 */
@Service("sysDictService")
public class DictServiceImpl extends ServiceImpl<IDictInfoDao, DictEntity> implements IDictService {


    @Autowired
    private IDictInfoDao dictDao;

    @Autowired
    private ICrudRedisManager<DictEntity> crudRedisManager;


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 14:30
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<DictEntity> page = this.page(
                new Query<DictEntity>().getPage(params),
                new QueryWrapper<DictEntity>()
                        .select("")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * @param key 字典表对应类型,redisKey为存储的key
     * @return list字典集合
     * @description: 根据字典type 查询字典数据
     * @author Gaosx By user
     * @date 2019/6/12 17:03
     */
    @Override
    public List<DictVo> findDicInfo(String key) {
        String dictString = crudRedisManager.hget(key, key, "获取字典key的集合,Redis异常,Exception{},异常信息为");
        if (dictString == null) {
            List<DictVo> dictVoList = dictDao.findDisByType(key);
            crudRedisManager.hset(key, key, JSON.toJSONString(dictVoList), "存储字典key的集合,Redis异常,Exception{},异常信息为");
            return dictVoList;
        } else {
            return JSON.parseArray(dictString, DictVo.class);
        }
    }

    /**
     * @param key 字典表对应类型
     * @return list字典集合
     * @description: 根据字典type 查询字典数据
     * @author Gaosx By user
     * @date 2019/6/12 17:03
     */
    @Override
    public String findDicValueCode(String key, String code) {
        String dictString = crudRedisManager.hget(key, code, "获取字典详情,Redis异常,Exception{},异常信息为");
        if (dictString == null) {
            dictString = dictDao.findDisByTypeAndCode(key, Integer.parseInt(code));
            if (dictString != null) {
                crudRedisManager.hset(key, code, dictString, "获取字典详情,Redis异常,Exception{},异常信息为");
            }
            return dictString;
        } else {
            return dictString;
        }
    }


}
