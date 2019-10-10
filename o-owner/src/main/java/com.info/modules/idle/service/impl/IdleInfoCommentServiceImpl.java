package com.info.modules.idle.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.idle.entity.IdleInfoCommentEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.idle.dao.IdleInfoCommentDao;
import com.info.modules.idle.service.IdleInfoCommentService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 闲置交易评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Service("idleInfoCommentService")
public class IdleInfoCommentServiceImpl extends ServiceImpl<IdleInfoCommentDao, IdleInfoCommentEntity> implements IdleInfoCommentService {


    @Autowired
    private ICrudRedisManager<IdleInfoCommentEntity> crudRedisManager;


    /**
     * 功能描述: 闲置交易评论表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 19:48
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String leId = (String) params.get("leId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<IdleInfoCommentEntity> page = this.page(
                new Query<IdleInfoCommentEntity>().getPage(params),
                new QueryWrapper<IdleInfoCommentEntity>()
                        .eq(StringUtils.isNotBlank(leId), "le_id", leId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 闲置交易评论删除接口--被评论不可删除
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 19:46
     * @Return:
     */
    @Override
    public ResultMessage delIdleComment(Integer commentId, Integer idleId) {
        crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_COMMENT, idleId.toString(), "闲置交易评论删除,Redis异常,Exception{},异常信息为:");
        Integer count = this.parentId(commentId);
        if (count > 0) {
            return ResultMessage.error(ConfigConstant.ERRORS, "级联评论不能被删除");
        }
        Boolean flag = this.removeById(commentId);
        if (flag) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: 查询闲置交易评论集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 20:33
     * @Return:
     */
    @Override
    public List<IdleInfoCommentEntity> commentList(Integer idleId) {
        //redis获取该信息所有的评论信息集合
        String comment = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.IDLE_COMMENT, idleId.toString(), "获取闲置信息评论集合,Redis异常,Exception{},异常信息为:");
        if (comment == null) {
            List<IdleInfoCommentEntity> commentEntityList = this.list(new QueryWrapper<IdleInfoCommentEntity>()
                    .eq("le_id", idleId)
            );
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.IDLE_COMMENT, idleId.toString(), JSON.toJSONString(commentEntityList), "存储闲置信息评论集合,Redis异常,Exception{},异常信息为:");
            return commentEntityList;
        } else {
            return JSON.parseArray(comment, IdleInfoCommentEntity.class);
        }
    }

    /**
     * 功能描述: 发布评论
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:27
     * @Return:
     */
    @Override
    public ResultMessage saveIdleComment(IdleInfoCommentEntity idleInfoCommentEntity) {
        Long flag = crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_COMMENT, idleInfoCommentEntity.getLeId().toString(), "闲置交易评论删除,Redis异常,Exception{},异常信息为:");
        if (flag <= 0) {
            crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_COMMENT, idleInfoCommentEntity.getLeId().toString(), "闲置交易评论删除,Redis异常,Exception{},异常信息为:");
        }
        Boolean status = this.save(idleInfoCommentEntity);
        if (status) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 判断commentId下面是否有被评论
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 20:12
     * @Return:
     */
    private Integer parentId(Integer commentId) {
        Integer count = this.count(new QueryWrapper<IdleInfoCommentEntity>()
                .select("comment_id")
                .eq("parent_id", commentId)
        );
        return count;
    }

}
