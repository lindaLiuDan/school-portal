package com.info.modules.idle.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.idle.dao.IdleInfoImgDao;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.modules.idle.service.IdleInfoImgService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 闲置信息图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Service("idleInfoImgService")
public class IdleInfoImgServiceImpl extends ServiceImpl<IdleInfoImgDao, IdleInfoImgEntity> implements IdleInfoImgService {


    @Autowired
    private ICrudRedisManager<IdleInfoImgEntity> crudRedisManager;


    /**
     * 功能描述: 闲置信息图片表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/22 15:53
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<IdleInfoImgEntity> page = this.page(
                new Query<IdleInfoImgEntity>().getPage(params),
                new QueryWrapper<IdleInfoImgEntity>()
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据idleId查询对应的图片信息集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 20:26
     * @Return:
     */
    @Override
    public List<IdleInfoImgEntity> imgList(Integer idleId) {
        //redis获取该信息的图片集合
        String imgs = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.IDLE_IMG, idleId.toString(), "获取闲置交易信息图片集合,Redis异常,Exception{},异常信息为:");
        if (imgs != null) {
            return JSON.parseArray(imgs, IdleInfoImgEntity.class);
        } else {
            List<IdleInfoImgEntity> imgList = this.list(new QueryWrapper<IdleInfoImgEntity>()
                    .select("img_id,le_id,img,small_img")
                    .eq("le_id", idleId)
            );
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.IDLE_IMG, idleId.toString(), JSON.toJSONString(imgList), "存储闲置交易信息图片集合,Redis异常,Exception{},异常信息为:");
            return imgList;
        }
    }

    /**
     * 功能描述: 删除图片集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:13
     * @Return:
     */
    @Override
    public Boolean delImgList(Integer idleId) {
        Long flag = crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_IMG, "删除闲置交易信息图片集合,Redis异常,Exception{},异常信息为:", idleId.toString());
        if (flag <= 0) {
            crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_IMG, "删除闲置交易信息图片集合,Redis异常,Exception{},异常信息为:", idleId.toString());
        }
        Boolean status = this.remove(new QueryWrapper<IdleInfoImgEntity>()
                .eq("le_id", idleId)
        );
        if (status) {
            return status;
        }
        return false;
    }



}
