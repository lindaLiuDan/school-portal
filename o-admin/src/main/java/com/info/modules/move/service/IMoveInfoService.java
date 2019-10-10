package com.info.modules.move.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.vo.MoveVo;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
public interface IMoveInfoService extends IService<MoveInfoEntity> {

    /**
     * 功能描述: 社区活动信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:02
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:03
     * @Return:
     */
    MoveInfoEntity getMoveInfo(Integer moveId);

    /**
     * 功能描述: 修改活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:25
     * @Return:
     */
    ResultMessage updateMoveInfo(MoveInfoEntity moveInfoEntity);

    /**
     * 功能描述: 删除活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:25
     * @Return:
     */
    ResultMessage delMoveInfo(Integer moveId);

}

