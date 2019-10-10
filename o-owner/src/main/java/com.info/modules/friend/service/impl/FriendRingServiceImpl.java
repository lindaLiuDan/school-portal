package com.info.modules.friend.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.friend.entity.FriendRingEntity;
import com.info.modules.friend.entity.FriendRingImgEntity;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.modules.friend.form.FriendsRingForm;
import com.info.modules.friend.vo.FriendRingVo;
import com.info.modules.friend.vo.ImgRingVo;
import com.info.modules.friend.vo.UserRingLikeVo;
import com.info.img.FileImgEntity;
import com.info.img.FileUploadUtil;
import com.info.img.ThumbnailatorUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.friend.dao.IFriendRingCommentDao;
import com.info.modules.friend.dao.IFriendRingDao;
import com.info.modules.friend.dao.IFriendRingImgDao;
import com.info.modules.friend.dao.IFriendRingLikeDao;
import com.info.modules.friend.service.IFriendRingService;
import com.info.modules.user.service.ImgService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
@Service("friendRingService")
public class FriendRingServiceImpl extends ServiceImpl<IFriendRingDao, FriendRingEntity> implements IFriendRingService {
    private static final Logger logger = LoggerFactory.getLogger(FriendRingServiceImpl.class);
    @Autowired
    private IFriendRingDao friendRingDao;

    @Autowired
    private IFriendRingImgDao friendRingImgDao;

    @Autowired
    private IFriendRingLikeDao friendRingLikeDao;

    @Autowired
    private IFriendRingCommentDao friendRingCommentDao;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    @Autowired
    private ImgService imgService;

    @Autowired
    private FriendRingImgServiceImpl ringImgService;


    @Autowired
    private ICrudRedisManager<FriendRingEntity> crudRedisManager;

    @Autowired
    private FileUploadUtil fileUploadUtil;

    /**
     * @Author: Gaosx
     * @Description: 社区朋友圈信息表
     * @Date: 2019-06-06 18:05:30
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = params.get("infoId").toString();
        String likeId = params.get("userId").toString();
        IPage page = new Query().getPage(params);

        List<FriendRingEntity> pageList = friendRingDao.getPageList(params);
        Integer total = friendRingDao.getPageTotal(Integer.valueOf(infoId));

        List<FriendRingVo> list = new ArrayList<>();
        pageList.stream().forEach(friendRingEntity -> {
            FriendRingVo friendRingVo = new FriendRingVo();
            BeanUtils.copyProperties(friendRingEntity, friendRingVo);

            List<FriendRingImgEntity> imgList = friendRingImgDao.getImgByInfRingId(friendRingEntity.getRingId());
            if (imgList.size() > 0) {
                List<ImgRingVo> listImg = new ArrayList<>();
                imgList.stream().forEach(friendRingImgEntity -> {
                    ImgRingVo imgRingVos = new ImgRingVo();
                    BeanUtils.copyProperties(friendRingImgEntity, imgRingVos);
                    listImg.add(imgRingVos);
                });
                friendRingVo.setImgList(listImg);
            }

            List<UserRingLikeVo> userList = friendRingLikeDao.getUserById(friendRingEntity.getRingId());
            if (userList.size() > 0) {
                List<UserRingLikeVo> listUser = new ArrayList<>();
                userList.stream().forEach(userRingLikeVo -> {
                    UserRingLikeVo vo = new UserRingLikeVo();
                    BeanUtils.copyProperties(userRingLikeVo, vo);
                    listUser.add(vo);
                });
                friendRingVo.setUserList(listUser);
            }
            //查询是否已点赞
            FriendRingLikeEntity entityByUId = friendRingLikeDao.getEntityByUId(Integer.valueOf(likeId), friendRingEntity.getRingId());
            if (entityByUId == null) {
                friendRingVo.setRingLikeIsOrNot(ConfigConstant.ERROR);
            } else {
                friendRingVo.setRingLikeIsOrNot(ConfigConstant.NOTDEL);
            }


            list.add(friendRingVo);
        });
        page.setRecords(list);
        page.setTotal(total);
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
    public FriendRingVo getDetail(Integer ringId, Integer userId) {

        FriendRingVo friendRingVo = crudRedisManager.hget(RedisKeyUtils.Friends.DETAIL, ringId.toString(), FriendRingVo.class, "获取朋友圈详情失败，redis异常，Exception{},异常信息为：");
        if (friendRingVo == null) {
            FriendRingEntity friendRingEntity = friendRingDao.getDeatil(ringId);
            friendRingVo = new FriendRingVo();
//            BeanUtils.copyProperties(friendRingEntity, friendRingVo);
            friendRingVo.setRingId(ringId);
            friendRingVo.setContent(friendRingEntity.getContent());
            friendRingVo.setInfoId(friendRingEntity.getInfoId());
            friendRingVo.setUserId(friendRingEntity.getUserId());
            friendRingVo.setUserName(friendRingEntity.getUserName());
            friendRingVo.setCreatorTime(friendRingEntity.getCreatorTime());
            friendRingVo.setImg(friendRingEntity.getImg());
            friendRingVo.setLikeNum(friendRingEntity.getLikeNum());
            List<FriendRingImgEntity> imgList = friendRingImgDao.getImgByInfRingId(friendRingEntity.getRingId());
            if (imgList != null) {
                List<ImgRingVo> listImg = new ArrayList<>();
                imgList.stream().forEach(friendRingImgEntity -> {
                    ImgRingVo imgRingVos = new ImgRingVo();
                    BeanUtils.copyProperties(friendRingImgEntity, imgRingVos);
                    listImg.add(imgRingVos);
                });
                friendRingVo.setImgList(listImg);
            }
            //查询点赞数量
            String s = crudRedisManager.hget(RedisKeyUtils.Friends.LIKE, friendRingEntity.getUserId() + ":" + ringId, "向redis中获取点赞数量数据错误,数据详情{},异常信息为");
            if (s != null) {
                friendRingVo.setLikeNum(Integer.valueOf(s));
            } else {
                friendRingVo.setLikeNum(friendRingEntity.getLikeNum());
            }
            crudRedisManager.hset(RedisKeyUtils.Friends.DETAIL, ringId.toString(), JSON.toJSONString(friendRingVo), "存储朋友圈详情失败异常,Exception{},异常信息为:");
        }

        //查询点赞数量
        String s = crudRedisManager.hget(RedisKeyUtils.Friends.LIKE, friendRingVo.getUserId() + ":" + ringId, "向redis中获取点赞数量数据错误,数据详情{},异常信息为");
        if (s!=null) {
            friendRingVo.setLikeNum(Integer.valueOf(s));
        } else {
            friendRingVo.setLikeNum(friendRingVo.getLikeNum());
        }
        List<UserRingLikeVo> userList = friendRingLikeDao.getUserById(ringId);
        if (userList != null) {
            List<UserRingLikeVo> listUser = new ArrayList<>();
            userList.stream().forEach(userRingLikeVo -> {
                UserRingLikeVo vo = new UserRingLikeVo();
                BeanUtils.copyProperties(userRingLikeVo, vo);
                listUser.add(vo);
            });
            friendRingVo.setUserList(listUser);
        }


        //查询是否已点赞
        FriendRingLikeEntity entityByUId = friendRingLikeDao.getEntityByUId(userId, ringId);
        if (entityByUId == null) {
            friendRingVo.setRingLikeIsOrNot(2);
        } else {
            friendRingVo.setRingLikeIsOrNot(1);
        }
//        //评论列表
//        List<FriendRingCommentEntity> commentByPId = friendRingCommentDao.findAllCommentByPId(friendRingVo.getRingId(), ConfigConstant.PARENTID);
//        friendRingVo.setList(commentByPId);
        return friendRingVo;
    }


    /**
     * @Description 发布朋友圈
     * @Author LiuDan
     * @Date 2019/6/22 15:35
     * @Param
     * @Return
     * @Exception
     */
    @Override
    @Transactional
    public ResultMessage saveFriendsRing(FriendsRingForm form) {
        FriendRingEntity entity = new FriendRingEntity();
        entity.setInfoId(form.getInfoId());
        entity.setUserId(form.getUserId());
        entity.setContent(form.getContent());
        entity.setCreatorTime(DateUtils.now());
        boolean b = this.save(entity);
        if (b) {
            MultipartFile[] file = form.getImg();
            List<FileImgEntity> list = fileUploadUtil.uploadImgList(file);
            list.stream().forEach(fileImgEntity -> {
                FriendRingImgEntity imgEntity = new FriendRingImgEntity();
                imgEntity.setImg(fileImgEntity.getImg());
                imgEntity.setSmallImg(fileImgEntity.getSmallImg());
                imgEntity.setRingId(entity.getRingId());
                imgEntity.setCreatorTime(DateUtils.now());
                ringImgService.save(imgEntity);
            });
            return ResultMessage.ok();
        }

        return ResultMessage.err();
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
}
