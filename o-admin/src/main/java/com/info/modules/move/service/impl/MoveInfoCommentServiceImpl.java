package com.info.modules.move.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.move.dao.IMoveInfoCommentDao;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import com.info.modules.move.service.IMoveInfoCommentService;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区活动留言表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Service("moveInfoCommentService")
public class MoveInfoCommentServiceImpl extends ServiceImpl<IMoveInfoCommentDao, MoveInfoCommentEntity> implements IMoveInfoCommentService {


    @Autowired
    private ICrudRedisManager<MoveInfoCommentEntity> crudRedisManager;


    /**
     * 功能描述: 社区活动留言表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 15:02
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        IPage page = new Query().getPage(params);
        List<MoveInfoCommentEntity> pageList = baseMapper.getPageList(params);
        Integer total = baseMapper.getPageTotal(params);
        page.setRecords(pageList);
        page.setTotal(total);
        return new PageUtils(page);
    }

}
