package com.info.modules.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.provider.entity.ProviderGradeEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.provider.dao.IProviderGradeDao;
import com.info.modules.provider.service.IProviderGradeService;
import com.info.modules.provider.vo.ProviderGradeVO;
import com.info.redis.RedisKeyUtils;
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
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Service("providerGradeService")
public class ProviderGradeServiceImpl extends ServiceImpl<IProviderGradeDao, ProviderGradeVO> implements IProviderGradeService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(ProviderGradeServiceImpl.class);

    @Autowired
    private ICrudRedisManager<ProviderGradeVO> crudRedisManager;


    /**
     * 功能描述: 商家(店铺)等级表
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
        IPage<ProviderGradeVO> page = this.page(
                new Query<ProviderGradeVO>().getPage(params),
                new QueryWrapper<ProviderGradeVO>()
                        .select("grade_id,gname,privilege")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 获取所有店铺等级集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:59
     * @Return:
     */
    @Override
    public List<ProviderGradeVO> getProviderGradeList(Map<String, Object> params) {
        String gradeString = crudRedisManager.get(RedisKeyUtils.ProviderKeys.GRADE_LIST, "获取所有店铺等级集合,Redis异常,Exception{},异常信息为:");
        if (gradeString == null) {
            List<ProviderGradeVO> list = this.list();
            crudRedisManager.set(RedisKeyUtils.ProviderKeys.GRADE_LIST, JSON.toJSONString(list), "获取所有店铺等级集合,Redis异常,Exception{},异常信息为:");
            return list;
        }
        return JSON.parseArray(gradeString, ProviderGradeVO.class);
    }

    /**
     * 功能描述: 获取单个店铺等级详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 18:06
     * @Return:
     */
    @Override
    public ProviderGradeVO getProviderGradeById(Integer gradeId) {
        ProviderGradeVO gradeVO = crudRedisManager.hget(RedisKeyUtils.ProviderKeys.GRADE_INFO, gradeId.toString(), ProviderGradeVO.class, "获取所有店铺等级详情,Redis异常,Exception{},异常信息为:");
        if (gradeVO == null) {
            gradeVO = this.getById(gradeId);
            crudRedisManager.hset(RedisKeyUtils.ProviderKeys.GRADE_INFO, gradeId.toString(), JSON.toJSONString(gradeVO), "获取所有店铺等级详情,Redis异常,Exception{},异常信息为:");
            return gradeVO;
        }
        return gradeVO;
    }

}
