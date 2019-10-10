package com.info.modules.move.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.move.entity.MoveInfoSignupEntity;
import com.info.modules.move.form.MoveInfoSignForm;
import com.info.modules.move.vo.MoveVo;
import com.info.modules.move.dao.IMoveInfoSignupDao;
import com.info.modules.move.service.IMoveInfoSignupService;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private IMoveInfoSignupDao signupDao;

    /**
     * @Author: Gaosx
     * @Description: 社区活动报名表
     * @Date: 2019-06-06 18:05:23
     */
    @Override
    public Integer queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
//        Page<MoveInfoSignupEntity> page = this.selectPage(
//                new Query<MoveInfoSignupEntity>(params).getPage(),
//                //TODO mamopi的这玩意挺好用的你呢 小子？妹子？汉子？女汉子？
//                //logger.info("----------------------------------傻子注意了请看该方法的业务层实现方法-----------------------------------")
//                new EntityWrapper<MoveInfoSignupEntity>().setSqlSelect("请填入需要查询的字段，如果是全表查询则可以删除掉，否则将会报错误信息！！！！！！！")
//                        // TODO 几乎所有的表都带IS_DEL字段所以是 这个条件直接生成了 ，如果特殊需要的话可以去掉
//                        .eq("is_del", 1)
//                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
//                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
//                        .like(StringUtils.isNotBlank(parames), "parames", parames)
//        );
        return 1;
    }


    /**
     * @Description 活动报名
     * @Author LiuDan
     * @Date 2019/6/10 11:53
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage saveSign(MoveInfoSignForm form) {
        //根据活动id 和报名人id 查询此用户是否已报过
        MoveInfoSignupEntity one = this.getOne(new QueryWrapper<MoveInfoSignupEntity>().eq("user_id", form.getUserId()).eq("move_id", form.getMoveId()));
        if(one!=null){
            return ResultMessage.ok("您已报过此活动不能重复报名！");
        }
        MoveInfoSignupEntity signupEntity = new MoveInfoSignupEntity();
        BeanUtils.copyProperties(form, signupEntity);
        signupEntity.setCreatorTime(DateUtils.now());
        boolean save = this.save(signupEntity);
        if (save) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }


    /**
     * @Description 个人中心——查询我参加的活动
     * @Author LiuDan
     * @Date 2019/6/17 15:12
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage getMyJoinMove(Map<String, Object> params) {
        IPage page = new Query().getPage(params);
        Integer total = signupDao.findMyJoinMoveTotal(params);
        List<MoveVo> list = null;
        if (total > 0) {
            list = signupDao.findMyJoinMoveList(params);
            page.setTotal(total);
            page.setRecords(list);
            return ResultMessage.ok(page);
        }
        return ResultMessage.ok(null);
    }

}
