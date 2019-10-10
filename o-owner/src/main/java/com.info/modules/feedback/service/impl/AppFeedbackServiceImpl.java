package com.info.modules.feedback.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.feedback.entity.AppFeedbackEntity;
import com.info.modules.feedback.form.SaveAppFeedbackForm;
import com.info.img.ThumbnailatorUtils;
import com.info.modules.feedback.dao.IAppFeedbackDao;
import com.info.modules.feedback.service.IAppFeedbackService;
import com.info.modules.user.service.ImgService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-11-14 17:59:26
 */
@Service("appFeedbackService")
public class AppFeedbackServiceImpl extends ServiceImpl<IAppFeedbackDao, AppFeedbackEntity> implements IAppFeedbackService {
    private static final Logger logger = LoggerFactory.getLogger(AppFeedbackServiceImpl.class);
    @Autowired
    private IAppFeedbackDao feedbackDao;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    @Autowired
    private ImgService imgService;

    /**
     * 功能描述:  分页查询反馈业务实现层
     *
     * @Author: Gaosx  960889426@qq.com
     * @Description:
     * @Date: 2018-11-14 17:59:26
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
//        Query query = new Query(params);
//        List pageList = feedbackDao.findPageList(query);
//        Integer total = feedbackDao.findPageTotal(query);
//        return new PageUtils(pageList, total, query.getLimit(), query.getCurrPage());
        return null;
    }

    /**
     * @param
     * @return
     * @description: 根据id 查询详情
     * @author liudan
     * @date 2019/4/26 15:37
     */
    @Override
    public AppFeedbackEntity getById(Integer feedblackId) {
        return feedbackDao.getById(feedblackId);
    }

    /**
     * @Description app端 --- 平台反馈
     * @Author LiuDan
     * @Date 2019/6/22 13:11
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage save(SaveAppFeedbackForm form) {
        AppFeedbackEntity feedbackEntity = new AppFeedbackEntity();
        BeanUtils.copyProperties(form, feedbackEntity);
        feedbackEntity.setCreateTime(new Date());
        //图片处理
        String img = null;
        String smallImg = null;
        if (form.getImg() != null) {
            try {
                Map<String, Object> stringObjectMap = thumbnailatorUtils.uploadFile(form.getImg());
                Map<String, Object> map = imgService.uploadFile(stringObjectMap);
                img = map.get("bigImgUrl").toString();
                smallImg = map.get("smallImgUrl").toString();

            } catch (Exception e) {
                logger.error("申请合作上传营业执照异常 ,用户id:{}, 异常信息为: ", form.getUserId(), e);
            }
        }
        feedbackEntity.setImg(img);
        feedbackEntity.setSmallImg(smallImg);
        this.save(feedbackEntity);
        return ResultMessage.ok();
    }

}
