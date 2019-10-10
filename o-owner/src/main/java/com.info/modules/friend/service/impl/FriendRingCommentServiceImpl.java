package com.info.modules.friend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import com.info.modules.friend.dao.IFriendRingCommentDao;
import com.info.modules.friend.service.IFriendRingCommentService;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
    private IFriendRingCommentDao friendRingCommentDao;

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈评论表
     * @Date: 2019-06-06 18:05:25
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        IPage page = new Query().getPage(params);

        Integer total = friendRingCommentDao.findAllTotal(params);
        if (total > 0) {
            List<FriendRingCommentEntity> list = friendRingCommentDao.findAllCommentByPId(params);
            list.stream().forEach(friendRingCommentEntity -> {


                List<FriendRingCommentEntity> list1 = this.list(new QueryWrapper<FriendRingCommentEntity>()
                        .eq("parent_id", friendRingCommentEntity.getCommentId()));

                friendRingCommentEntity.getNewsList().stream().forEach(friendRingCommentVo -> {
                    friendRingCommentVo.setReplayName(friendRingCommentEntity.getUserName());
                });

                friendRingCommentEntity.setMore(list1.size());
            });
            page.setTotal(total);
            page.setRecords(list);
            return new PageUtils(page);
        } else {
            page.setTotal(total);
            page.setRecords(new ArrayList());
            return new PageUtils(page);
        }
    }

}
