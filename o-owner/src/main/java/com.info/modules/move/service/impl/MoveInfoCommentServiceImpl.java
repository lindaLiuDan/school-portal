package com.info.modules.move.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import com.info.modules.move.dao.IMoveInfoCommentDao;
import com.info.modules.move.service.IMoveInfoCommentService;
import com.info.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.Query;

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
    private IMoveInfoCommentDao commentDao;

    /**
     * @Author: Gaosx
     * @Description: 社区活动留言表
     * @Date: 2019-06-06 18:05:24
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage page = new Query().getPage(params);
        List<MoveInfoCommentEntity> pageList = commentDao.getPageList(params);
        Integer total = commentDao.getPageTotal(null);
        page.setRecords(pageList);
        page.setTotal(total);
        return new PageUtils(page);
    }

}
