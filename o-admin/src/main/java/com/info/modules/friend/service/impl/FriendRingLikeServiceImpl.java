package com.info.modules.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingLikeDao;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.modules.friend.service.IFriendRingLikeService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.Constant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Service("friendRingLikeService")
public class FriendRingLikeServiceImpl extends ServiceImpl<IFriendRingLikeDao, FriendRingLikeEntity> implements IFriendRingLikeService {

    @Autowired
    private ICrudRedisManager<FriendRingLikeEntity> crudRedisManager;


    /**
     * 功能描述: 社区朋友圈点赞表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 10:56
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<FriendRingLikeEntity> page = this.page(
                new Query<FriendRingLikeEntity>().getPage(params),
                new QueryWrapper<FriendRingLikeEntity>()
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 查看点赞人信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:16
     * @Return:
     */
    @Override
    public FriendRingLikeEntity getFriendRingLikeInfo(Integer likeId) {
        FriendRingLikeEntity friendRingLikeEntity = crudRedisManager.hget(RedisKeyUtils.Friends.FRIEND_INFO, likeId.toString(), FriendRingLikeEntity.class, "获取朋友圈点赞信息Friends.DETAIL:" + likeId + "异常:");
        if (friendRingLikeEntity == null) {
            friendRingLikeEntity = this.getById(likeId);
            if (friendRingLikeEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.Friends.FRIEND_INFO, likeId.toString(), JSON.toJSONString(friendRingLikeEntity), "存储朋友圈点赞信息Friends.DETAIL:" + likeId + "异常:");
            }
            return friendRingLikeEntity;
        }
        return friendRingLikeEntity;
    }

    /**
     * 功能描述: 删除点赞信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 11:31
     * @Return:
     */
    @Override
    public ResultMessage delFriendRingLike(Integer likeId) {
        return null;
    }


}
