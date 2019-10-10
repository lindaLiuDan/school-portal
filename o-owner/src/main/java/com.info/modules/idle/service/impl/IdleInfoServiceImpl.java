package com.info.modules.idle.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.info.modules.idle.entity.IdleInfoCommentEntity;
import com.info.modules.idle.entity.IdleInfoEntity;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.idle.dao.IdleInfoDao;
import com.info.modules.idle.service.IdleInfoCommentService;
import com.info.modules.idle.service.IdleInfoImgService;
import com.info.modules.idle.service.IdleInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Service("idleInfoService")
public class IdleInfoServiceImpl extends ServiceImpl<IdleInfoDao, IdleInfoEntity> implements IdleInfoService {


//    @Autowired
//    private IdleInfoDao idleInfoDao;

    @Autowired
    private IdleInfoImgService idleInfoImgService;

    @Autowired
    private IdleInfoCommentService idleInfoCommentService;

    @Autowired
    private ICrudRedisManager<IdleInfoEntity> crudRedisManager;


    /**
     * 功能描述: 闲置交易信息表--图片集合list
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 16:29
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        IPage<IdleInfoEntity> page = this.page(
                new Query<IdleInfoEntity>().getPage(params),
                new QueryWrapper<IdleInfoEntity>()
                        .select("le_id,info_id,price,mobile,user_id,creator_time,area,content,price")
                        .eq(StringUtils.isNotBlank(infoId), "info_id", infoId)
        );
        //图片信息集合
        page.getRecords().stream().forEach(info -> {
            List<IdleInfoImgEntity> imgList = idleInfoImgService.imgList(info.getLeId());
            info.setImgEntityList(imgList);
        });
        //评论信息集合
        page.getRecords().stream().forEach(info -> {
            List<IdleInfoCommentEntity> imgList = idleInfoCommentService.commentList(info.getLeId());
            info.setIdleInfoCommentEntityList(imgList);
        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据用户ID主键查询用户发布的闲置交易信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 15:31
     * @Return:
     */
    @Override
    public PageUtils idleList(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<IdleInfoEntity> page = this.page(
                new Query<IdleInfoEntity>().getPage(params),
                new QueryWrapper<IdleInfoEntity>()
                        .select("le_id,info_id,price,mobile,user_id,creator_time,area")
                        .eq("user_id", userId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        //图片信息集合
        page.getRecords().stream().forEach(info -> {
            List<IdleInfoImgEntity> imgList = idleInfoImgService.imgList(info.getLeId());
            info.setImgEntityList(imgList);
        });
        //评论信息集合
        page.getRecords().stream().forEach(info -> {
            List<IdleInfoCommentEntity> imgList = idleInfoCommentService.commentList(info.getLeId());
            info.setIdleInfoCommentEntityList(imgList);
        });
        return new PageUtils(page);
    }


    /**
     * 功能描述: 分页查询闲置信息list--暂时废弃
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 15:36
     * @Return:
     */
    @Override
    public PageUtils pageIdleInfo(Map<String, Object> params) {
//        Query query = new Query(params);
//        List<PromotionInfoEntity> list = promotionInfoDao.findPageList(query);
//        int totalPage = promotionInfoDao.findTotalPage(query);
//        list.stream().forEach(promotionInfoEntity -> {
//            if(promotionInfoEntity.getApplyType()!=null){
//                String applyTypeString= sysDictService.findDicValueCode(ConfigEnum.apply_type.toString(), RedisKeys.dict.DICT_INFO, promotionInfoEntity.getApplyType());
//                promotionInfoEntity.setApplyTypeString(applyTypeString);
//            }
//        });
//        return new PageUtils(list, totalPage, query.getLimit(), query.getCurrPage());
//        Query query = new Query();
//        List<IdleInfoEntity> list = idleInfoDao.pageIdleInfo(query);
//        Integer count = idleInfoDao.pageIdleInfoCount(query);
        return null;
    }

    /**
     * 功能描述: 闲置交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/10 13:39
     * @Return:
     */
    @Override
    public IdleInfoEntity loadById(Integer leId) {
        IdleInfoEntity idleInfoEntity = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.IDLE_INFO, leId.toString(), IdleInfoEntity.class, "闲置交易信息详情,Redis异常,Exception{},异常信息为:");
        if (idleInfoEntity == null) {
            idleInfoEntity = this.getById(leId);
            idleInfoEntity.setImgEntityList(idleInfoImgService.imgList(leId));
            idleInfoEntity.setIdleInfoCommentEntityList(idleInfoCommentService.commentList(leId));
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.IDLE_INFO, leId.toString(), JSON.toJSONString(idleInfoEntity), "存储闲置交易信息详情,Redis异常,Exception{},异常信息为:");
            return idleInfoEntity;
        }
        return idleInfoEntity;
    }

    /**
     * 功能描述: 用户发布交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/23 22:06
     * @Return:
     */
    @Override
    public ResultMessage saveIdleInfo(IdleInfoEntity idleInfoEntity) {

        return null;
    }

    /**
     * 功能描述: 用户删除发布交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 22:08
     * @Return:
     */
    @Override
    public ResultMessage delIdleInfo(Integer leId) {
        idleInfoImgService.delImgList(leId);
        idleInfoCommentService.delIdleComment(null, leId);
        crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.IDLE_INFO, leId.toString(), "删除闲置交易信息详情,Redis异常,Exception{},异常信息为:");
        IdleInfoEntity idleInfoEntity = new IdleInfoEntity();
        idleInfoEntity.setLeId(leId);
        idleInfoEntity.setIsDel(ConfigConstant.DEL);
        Boolean flag = this.updateById(idleInfoEntity);
        if (flag) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }


}
