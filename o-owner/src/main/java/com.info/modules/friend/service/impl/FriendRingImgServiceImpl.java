package com.info.modules.friend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.friend.entity.FriendRingImgEntity;
import com.info.modules.friend.dao.IFriendRingImgDao;
import com.info.modules.friend.service.IFriendRingImgService;
import org.springframework.stereotype.Service;

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

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈图片表
     * @Date: 2019-06-06 18:05:24
     */
    @Override
    public Integer queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
//        Page<FriendRingImgEntity> page = this.selectPage(
//                new Query<FriendRingImgEntity>(params).getPage(),
//                //TODO mamopi的这玩意挺好用的你呢 小子？妹子？汉子？女汉子？
//                //logger.info("----------------------------------傻子注意了请看该方法的业务层实现方法-----------------------------------")
//                new EntityWrapper<FriendRingImgEntity>().setSqlSelect("请填入需要查询的字段，如果是全表查询则可以删除掉，否则将会报错误信息！！！！！！！")
//                        // TODO 几乎所有的表都带IS_DEL字段所以是 这个条件直接生成了 ，如果特殊需要的话可以去掉
//                        .eq("is_del", 1)
//                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
//                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
//                        .like(StringUtils.isNotBlank(parames), "parames", parames)
//        );
        return 1;
    }

}
