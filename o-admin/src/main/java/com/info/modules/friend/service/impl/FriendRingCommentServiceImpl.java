package com.info.modules.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingCommentDao;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import com.info.modules.friend.service.IFriendRingCommentService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
@Service("friendRingCommentService")
public class FriendRingCommentServiceImpl extends ServiceImpl<IFriendRingCommentDao, FriendRingCommentEntity> implements IFriendRingCommentService {

    @Autowired
    private ICrudRedisManager<FriendRingCommentEntity> crudRedisManager;

    /**
     * 功能描述: 社区朋友圈评论表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:01
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<FriendRingCommentEntity> page = this.page(
                new Query<FriendRingCommentEntity>().getPage(params),
                new QueryWrapper<FriendRingCommentEntity>()
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 删除评论信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:53
     * @Return:
     */
    @Override
    public ResultMessage delFriendRingComment(Integer commentId) {
        Boolean flag = this.removeById(commentId);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除评论信息失败...");
    }

    /**
     * 功能描述: 查看评论信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:58
     * @Return:
     */
    @Override
    public FriendRingCommentEntity getFriendRingComment(Integer commentId) {
        FriendRingCommentEntity friendRingCommentEntity = crudRedisManager.hget(RedisKeyUtils.Friends.FRIEND_COMMENT, commentId.toString(), FriendRingCommentEntity.class, "获取FRIEND_COMMENT：" + commentId + "，异常：");
        if (friendRingCommentEntity == null) {
            friendRingCommentEntity = this.getById(commentId);
            if (friendRingCommentEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.Friends.FRIEND_COMMENT, commentId.toString(), JSON.toJSONString(friendRingCommentEntity), "获取FRIEND_COMMENT：" + commentId + "，异常：");
            }
            return friendRingCommentEntity;
        }
        return friendRingCommentEntity;
    }

}
