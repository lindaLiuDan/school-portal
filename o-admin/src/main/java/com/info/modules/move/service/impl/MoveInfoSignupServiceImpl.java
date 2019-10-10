package com.info.modules.move.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.move.dao.IMoveInfoSignupDao;
import com.info.modules.move.entity.MoveInfoSignupEntity;
import com.info.modules.move.service.IMoveInfoSignupService;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
@Service("moveInfoSignupService")
public class MoveInfoSignupServiceImpl extends ServiceImpl<IMoveInfoSignupDao, MoveInfoSignupEntity> implements IMoveInfoSignupService {

    @Autowired
    private ICrudRedisManager<MoveInfoSignupEntity> crudRedisManager;


    /**
     * 功能描述: 社区活动报名表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:53
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<MoveInfoSignupEntity> page = this.page(
                new Query<MoveInfoSignupEntity>().getPage(params),
                new QueryWrapper<MoveInfoSignupEntity>()
                        .select("up_id,move_id,user_id,user_name,mobile,creator_time")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 活动报名信息详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:55
     * @Return:
     */
    @Override
    public MoveInfoSignupEntity getMoveInfoSignup(Integer upId) {

        return null;
    }


}
