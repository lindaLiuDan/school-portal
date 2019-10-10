package com.info.modules.move.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.vo.MoveVo;
import com.info.modules.move.dao.IMoveInfoDao;
import com.info.modules.move.service.IMoveInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
@Service("moveInfoService")
public class MoveInfoServiceImpl extends ServiceImpl<IMoveInfoDao, MoveInfoEntity> implements IMoveInfoService {

    @Autowired
    private IMoveInfoDao moveInfoDao;

    @Autowired
    private ICrudRedisManager<MoveInfoEntity> crudRedisManager;

    /**
     * @Author: Gaosx
     * @Description: 社区活动信息表
     * @Date: 2019-06-06 18:05:29
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage page = new Query().getPage(params);
        List<MoveVo> list = new ArrayList<>();
        Integer total = moveInfoDao.getMoveInfoTotal(params);
        if (total > 0) {
            list = moveInfoDao.getMoveInfoList(params);
        }
        page.setTotal(total);
        page.setRecords(list);
        return new PageUtils(page);
    }

    /**
     * @Description活动详情
     * @Author LiuDan
     * @Date 2019/6/10 8:13
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public MoveVo getDetail(Integer moveId) {
        MoveVo get = crudRedisManager.hget(RedisKeyUtils.move.DETAIL, String.valueOf(moveId), MoveVo.class, "活动详情查询Redis异常,Exception{},异常信息为");
        if (get == null) {
            MoveVo vo = moveInfoDao.getDetail(moveId);
            if (vo != null) {
                crudRedisManager.hset(RedisKeyUtils.move.DETAIL, String.valueOf(moveId),JSON.toJSONString(vo), "活动详情存入redis失败，Redis异常,Exception{},异常信息为");
                return vo;
            } else {
                return null;
            }
        } else {
            return get;
        }
    }
}
