package com.info.modules.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.friend.entity.FriendRingEntity;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingDao;
import com.info.modules.friend.dao.IFriendRingLikeDao;
import com.info.modules.friend.service.IFriendRingLikeService;
import com.info.modules.friend.service.IFriendRingService;
import com.info.redis.RedisKeyUtils;
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
    private IFriendRingLikeDao friendRingLikeDao;

    @Autowired
    private IFriendRingDao ringDao;

    @Autowired
    private IFriendRingService ringService;


    @Autowired
    private ICrudRedisManager<FriendRingLikeEntity> crudRedisManager;

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈点赞表
     * @Date: 2019-06-06 18:05:24
     */
    @Override
    public Integer queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
//        Page<FriendRingLikeEntity> page = this.selectPage(
//                new Query<FriendRingLikeEntity>(params).getPage(),
//                //TODO mamopi的这玩意挺好用的你呢 小子？妹子？汉子？女汉子？
//                //logger.info("----------------------------------傻子注意了请看该方法的业务层实现方法-----------------------------------")
//                new EntityWrapper<FriendRingLikeEntity>().setSqlSelect("请填入需要查询的字段，如果是全表查询则可以删除掉，否则将会报错误信息！！！！！！！")
//                        // TODO 几乎所有的表都带IS_DEL字段所以是 这个条件直接生成了 ，如果特殊需要的话可以去掉
//                        .eq("is_del", 1)
//                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
//                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
//                        .like(StringUtils.isNotBlank(parames), "parames", parames)
//        );
        return 1;
    }


    /**
     * @Description 朋友圈点赞
     * @Author LiuDan
     * @Date 2019/6/11 15:32
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage saveRingLike(FriendRingLikeEntity friendRingLike) {
        //先判断这个用户是否点赞了这个消息   如果点赞视为取消
        FriendRingLikeEntity entity = this.getOne(new QueryWrapper<FriendRingLikeEntity>().eq("ring_id", friendRingLike.getRingId()));
        if (entity == null) {
            FriendRingEntity ringEntity = ringService.getById(friendRingLike.getRingId());
            //更改ringInfo 中的点赞数量
            FriendRingEntity friendRingEntity = new FriendRingEntity();
            friendRingEntity.setRingId(friendRingLike.getRingId());
            friendRingEntity.setLikeNum(ringEntity.getLikeNum() + 1);
            boolean b1 = ringService.updateById(friendRingEntity);
            if (!b1) {
                return ResultMessage.error("更新朋友圈表中点赞数量失败");
            }
            Integer userId = friendRingLike.getUserId();
            Integer ringId = friendRingLike.getRingId();

            String s = crudRedisManager.hget(RedisKeyUtils.Friends.LIKE, userId + ":" + ringId, "向redis中获取点赞数量数据错误,数据详情{},异常信息为");
            if (StringUtils.isNotBlank(s)) {
                crudRedisManager.atomAddOrMinus(RedisKeyUtils.Friends.LIKE, Integer.valueOf(userId + ":" + ringId), 1, "向redis中添加点赞数量数据错误,数据详情{},异常信息为");
            } else {
                FriendRingEntity entity1 = ringService.getById(ringId);
                crudRedisManager.hset(RedisKeyUtils.Friends.LIKE, userId + ":" + ringId, entity1.getLikeNum().toString(), "向redis中保存点赞数量数据错误,数据详情{},异常信息为");
            }


            //添加点赞表
            FriendRingLikeEntity likeEntity = new FriendRingLikeEntity();
            likeEntity.setRingId(ringId);
            likeEntity.setUserId(userId);
            likeEntity.setCreatorTime(DateUtils.now());
            boolean b = this.save(likeEntity);
            if (b) {
                return ResultMessage.ok();
            }
        } else {
            //取消点赞
            FriendRingEntity ringEntity = ringService.getById(friendRingLike.getRingId());
            //更改ringInfo 中的点赞数量
            FriendRingEntity ringEntity1 = new FriendRingEntity();
            ringEntity1.setRingId(friendRingLike.getRingId());
            ringEntity1.setLikeNum(ringEntity.getLikeNum() - 1);
            boolean b1 = ringService.updateById(ringEntity1);
            if (!b1) {
                return ResultMessage.error("更新朋友圈表中点赞数量失败");
            }

            String s = crudRedisManager.hget(RedisKeyUtils.Friends.LIKE, friendRingLike.getUserId() + ":" + friendRingLike.getRingId(), "向redis中获取点赞数量数据错误,数据详情{},异常信息为");
            if (StringUtils.isNotBlank(s)) {
                crudRedisManager.atomAddOrMinus(RedisKeyUtils.Friends.LIKE, Integer.valueOf(friendRingLike.getUserId() + ":" + friendRingLike.getRingId()), 1, "向redis中添加点赞数量数据错误,数据详情{},异常信息为");
            } else {
                FriendRingEntity entity1 = ringService.getById(friendRingLike.getRingId());
                crudRedisManager.hset(RedisKeyUtils.Friends.LIKE, friendRingLike.getUserId() + ":" + friendRingLike.getRingId(), entity1.getLikeNum().toString(), "向redis中保存点赞数量数据错误,数据详情{},异常信息为");
            }


            //取消点赞表
            boolean b = this.remove(new QueryWrapper<FriendRingLikeEntity>().eq("ring_id", friendRingLike.getRingId()).eq("user_id", friendRingLike.getUserId()));
            if (b) {
                return ResultMessage.ok();
            }
        }
        return ResultMessage.error("点赞失败");
    }


}
