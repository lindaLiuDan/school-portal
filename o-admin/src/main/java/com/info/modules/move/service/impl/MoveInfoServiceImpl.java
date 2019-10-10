package com.info.modules.move.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.move.dao.IMoveInfoDao;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.service.IMoveInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ICrudRedisManager<MoveInfoEntity> crudRedisManager;


    /**
     * 功能描述: 社区活动信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:02
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<MoveInfoEntity> page = this.page(
                new Query<MoveInfoEntity>().getPage(params),
                new QueryWrapper<MoveInfoEntity>()
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:03
     * @Return:
     */
    @Override
    public MoveInfoEntity getMoveInfo(Integer moveId) {
        MoveInfoEntity moveInfoEntity = crudRedisManager.hget(RedisKeyUtils.move.DETAIL, moveId.toString(), MoveInfoEntity.class, "活动详情move.DETAIL," + moveId + "异常:");
        if (moveInfoEntity == null) {
            moveInfoEntity = this.getById(moveId);
            if (moveInfoEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.move.DETAIL, moveId.toString(), JSON.toJSONString(moveInfoEntity), "存储活动详情move.DETAIL," + moveId + "异常:");
                return moveInfoEntity;
            }
        }
        return moveInfoEntity;
    }

    /**
     * 功能描述: 修改活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:25
     * @Return:
     */
    @Override
    public ResultMessage updateMoveInfo(MoveInfoEntity moveInfoEntity) {
        Boolean flag = this.updateById(moveInfoEntity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.move.DETAIL, moveInfoEntity.getMoveId().toString(), "修改活动详情move.DETAIL,异常:");
            return ResultMessage.ok("修改活动信息成功");
        }
        return ResultMessage.error(ConfigConstant.ERROR, "修改失败");
    }

    /**
     * 功能描述: 删除活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:25
     * @Return:
     */
    @Override
    public ResultMessage delMoveInfo(Integer moveId) {
        Boolean flag = this.removeById(moveId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.move.DETAIL, moveId.toString(), "删除活动详情move.DETAIL,异常:");
            return ResultMessage.ok("删除活动信息成功");
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除失败");
    }
}
