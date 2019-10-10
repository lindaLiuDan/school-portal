package com.info.modules.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.img.FileImgEntity;
import com.info.img.FileUploadUtil;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingDao;
import com.info.modules.friend.entity.FriendRingEntity;
import com.info.modules.friend.entity.FriendRingImgEntity;
import com.info.modules.friend.service.IFriendRingImgService;
import com.info.modules.friend.service.IFriendRingService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
@Service("friendRingService")
public class FriendRingServiceImpl extends ServiceImpl<IFriendRingDao, FriendRingEntity> implements IFriendRingService {

    @Autowired
    private ICrudRedisManager<FriendRingEntity> crudRedisManager;

    @Autowired
    private IFriendRingImgService friendRingImgService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈信息表
     * @Date: 2019-06-06 18:05:30
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        String likeId = (String) params.get("userId");
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<FriendRingEntity> page = this.page(
                new Query<FriendRingEntity>().getPage(params),
                new QueryWrapper<FriendRingEntity>()
                        .eq(StringUtils.isNotBlank(infoId), "infoId", infoId)
                        .eq(StringUtils.isNotBlank(likeId), "likeId", likeId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }


    /**
     * @Description 查询朋友圈详情
     * @Author LiuDan
     * @Date 2019/6/11 14:41
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public FriendRingEntity getDetail(Integer ringId, Integer userId) {
        FriendRingEntity friendRingEntity = crudRedisManager.hget(RedisKeyUtils.Friends.FRIEND_INFO, ringId.toString(), FriendRingEntity.class, "获取朋友圈详情Friends.DETAIL:" + ringId + ",异常：");
        if (friendRingEntity == null) {
            friendRingEntity = this.getById(ringId);
            if (friendRingEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.Friends.FRIEND_INFO, ringId.toString(), JSON.toJSONString(friendRingEntity), "存储朋友圈详情Friends.DETAIL:" + ringId + ",异常：");
            }
            friendRingEntity.setFriendRingImgEntities(this.getAllFriendRingImg(ringId));
            return friendRingEntity;
        }
        friendRingEntity.setFriendRingImgEntities(this.getAllFriendRingImg(ringId));
        return friendRingEntity;
    }

    /**
     * 功能描述: 更具朋友圈信息获取朋友圈图片集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 12:11
     * @Return:
     */
    private List<FriendRingImgEntity> getAllFriendRingImg(Integer ringId) {
        return friendRingImgService.getFriendRingImg(ringId);
    }


    /**
     * @param
     * @return
     * @description: 上传图片
     * @author liudan
     * @date 2019/4/19 15:36
     */
    @Override
    public ResultMessage upload(MultipartFile[] file) {
        List<FileImgEntity> list = fileUploadUtil.uploadImgList(file);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 删除朋友圈根据ID
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 12:21
     * @Return:
     */
    @Override
    public ResultMessage delFriendRing(Integer ringId, Integer userId) {
        FriendRingEntity friendRingEntity = new FriendRingEntity();
        friendRingEntity.setRingId(ringId);
        friendRingEntity.setUserId(userId);
        friendRingEntity.setIsDel(ConfigConstant.DEL);
        Boolean flag = this.updateById(friendRingEntity);
        if (flag) {
            friendRingImgService.delFriendRingImgList(ringId);
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }
}
