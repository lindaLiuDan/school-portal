package com.info.modules.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingImgDao;
import com.info.modules.friend.entity.FriendRingImgEntity;
import com.info.modules.friend.service.IFriendRingImgService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Service("friendRingImgService")
public class FriendRingImgServiceImpl extends ServiceImpl<IFriendRingImgDao, FriendRingImgEntity> implements IFriendRingImgService {


    @Autowired
    private ICrudRedisManager<FriendRingImgEntity> crudRedisManager;


    /**
     * 功能描述: 社区朋友圈图片表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:00
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<FriendRingImgEntity> page = this.page(
                new Query<FriendRingImgEntity>().getPage(params),
                new QueryWrapper<FriendRingImgEntity>()
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 删除图片的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:34
     * @Return:
     */
    @Override
    public ResultMessage delFriendRingImg(Integer imgId, Integer ringId) {
        Boolean flag = this.removeById(imgId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.Friends.FRIEND_IMG, ringId.toString(), "删除图片Friends.FRIEND_IMG:" + imgId + "异常:");
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除图片失败...");
    }

    /**
     * 功能描述: 根据ID查询所有图片的集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 12:13
     * @Return:
     */
    @Override
    public List<FriendRingImgEntity> getFriendRingImg(Integer ringId) {
        String imgList = crudRedisManager.hget(RedisKeyUtils.Friends.FRIEND_IMG, ringId.toString(), "获取图片Friends.FRIEND_IMG:" + ringId + "异常:");
        if (imgList == null) {
            List<FriendRingImgEntity> list = this.list(new QueryWrapper<FriendRingImgEntity>()
                    .eq("ring_id", ringId)
            );
            return list;
        }
        return JSON.parseArray(imgList, FriendRingImgEntity.class);
    }

    /**
     * 功能描述: 根据ringId删除图片集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:34
     * @Return:
     */
    @Override
    public ResultMessage delFriendRingImgList(Integer ringId) {
        Boolean flag = this.removeById(ringId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.Friends.FRIEND_IMG, ringId.toString(), "删除图片集合Friends.FRIEND_IMG:" + ringId + "异常:");
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

}

