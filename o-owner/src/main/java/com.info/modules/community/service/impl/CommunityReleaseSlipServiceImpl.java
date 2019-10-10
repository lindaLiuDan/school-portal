package com.info.modules.community.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.dao.ICommunityReleaseSlipDao;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.modules.community.form.CommunityReleaseForm;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.modules.community.service.ICommunityReleaseSlipService;
import com.info.modules.community.vo.CommunityRedisVo;
import com.info.modules.community.vo.CommunityReleasePassAndApplyVo;
import com.info.modules.community.vo.CommunityReleaseVo;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.form.SendSMSForm;
import com.info.modules.user.service.ISendService;
import com.info.modules.user.service.IUserAuthService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
@Service("communityReleaseSlipService")
public class CommunityReleaseSlipServiceImpl extends ServiceImpl<ICommunityReleaseSlipDao, CommunityReleaseSlipEntity> implements ICommunityReleaseSlipService {


    @Autowired
    private ISendService sendService;

    @Autowired
    private IUserAuthService userAuthService;

    @Autowired
    private ICommunityBuildInfoService communityBuildInfoService;

    @Autowired
    private ICrudRedisManager<CommunityReleaseSlipEntity> crudRedisManager;

    @Autowired
    private ICommunityReleaseSlipDao releaseSlipDao;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description:
     * @Date: 2019-06-25 18:19:11
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        IPage<CommunityReleaseSlipEntity> page = this.page(new Query<CommunityReleaseSlipEntity>().getPage(params),
                new QueryWrapper<CommunityReleaseSlipEntity>().eq("user_id", userId)
                        .eq("slipResult", StringUtils.isNotBlank(params.get("slipResult").toString()))
        );
        return new PageUtils(page);
    }


    /**
     * @Description 添加电子放行单
     * @Author LiuDan
     * @Date 2019/6/25 19:08
     * @Param
     * @Return
     * @Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMessage saveEntity(CommunityReleaseForm form) {
        SendSMSForm sendSMSForm = new SendSMSForm();
        sendSMSForm.setMd(form.getMd());
        sendSMSForm.setMobile(form.getMobile());
        sendSMSForm.setTimes(form.getTimes());
        sendSMSForm.setTypeId(ConfigConstant.SEND_TYPE_PASS);
        sendSMSForm.setUserId(form.getUserId());
        sendSMSForm.setStartTime(form.getStartTime());
        sendSMSForm.setEndTime(form.getEndTime());
        //发送验证码
        ResultMessage send = sendService.send(sendSMSForm);
        String code = send.get("code").toString();
        if (code.equals(String.valueOf(ConfigConstant.NOTDEL))) {
            String numbers = send.get("result").toString();
            CommunityReleaseSlipEntity entity = new CommunityReleaseSlipEntity();
            entity.setCreatorTime(DateUtils.now());
            BeanUtils.copyProperties(form, entity);
            entity.setSlipCode(String.valueOf(numbers));
            entity.setMobile(form.getMobile());
            boolean b = this.save(entity);
            if (b) {
                //todo   后台审核  审核通过之后才能把验证码存放到redis中    写后台的时候需要注意咯~~~
//                //验证码和校验时间需要存到redis中
//                String key = RedisKeyUtils.CommunityReleaseSlip.MOBILE + numbers;
//                CommunityRedisVo redisVo = new CommunityRedisVo();
//                redisVo.setCode(numbers);
//                redisVo.setMobile(form.getMobile());
//                redisVo.setEndTime(entity.getEndTime());
//                long time = entity.getEndTime().getTime();
//                crudRedisManager.set(key, JSON.toJSONString(redisVo), time, "访客通行证的验证码放入redis失败，Redis异常,Exception{},异常信息为：");

                return ResultMessage.ok();
            }
        }
        return send;
    }

    /**
     * @Description 根据电话号码和当前时间判断这个短话是否过期
     * @Author LiuDan
     * @Date 2019/7/7 15:24
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public Boolean getEntityByMobile(String mobile, Date date) {
        CommunityReleaseSlipEntity entity = this.getOne(new QueryWrapper<CommunityReleaseSlipEntity>().eq("mobile", mobile).ge("end_time", date));
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * @Description 查询详情
     * @Author LiuDan
     * @Date 2019/6/26 9:30
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage getDetail(Integer userId, Integer slipId) {

        String key = RedisKeyUtils.CommunityReleaseSlip.SLIP_DETAIL;
        CommunityReleaseVo communityReleaseVo = crudRedisManager.hget(key, String.valueOf(slipId), CommunityReleaseVo.class, "获取电子放行单详情，Redis异常,Exception{},异常信息为");
        if (communityReleaseVo == null) {
            CommunityReleaseVo releaseVo = new CommunityReleaseVo();
            CommunityReleaseSlipEntity entity = this.getById(slipId);
            if (entity != null) {
                CommunityBuildInfoEntity buildInfo = communityBuildInfoService.getRoomInfo(entity.getBuildId());
                if (buildInfo != null) {
                    String infoName = buildInfo.getInfoName();
                    String floorName = buildInfo.getFloorName();
                    String unitName = buildInfo.getUnitName();
                    String levelName = buildInfo.getLevelName();
                    String roomName = buildInfo.getRoomName();
                    releaseVo.setInfoName(infoName);
                    releaseVo.setRoom(floorName + unitName + levelName + roomName);
                    releaseVo.setOpenTime(entity.getOpenTime());
                    releaseVo.setSlipCode(entity.getSlipCode());
                    releaseVo.setUserName(entity.getUserName());
                    releaseVo.setCard(entity.getCard());
                    final UserAuthEntity userById = userAuthService.getUserById(entity.getUserId());
                    releaseVo.setIdentity(userById.getIdentity());
                    releaseVo.setStartTime(entity.getStartTime());
                    releaseVo.setEndTime(entity.getEndTime());
                    releaseVo.setSlipResult(entity.getSlipResult());
                    releaseVo.setSlipCause(entity.getSlipCause());
                    releaseVo.setCheckResult(entity.getCheckResult());
                }
                crudRedisManager.hset(key, String.valueOf(slipId), JSON.toJSONString(releaseVo), "存放电子放行单详情，Redis异常,Exception{},异常信息为");
                return ResultMessage.ok(releaseVo);
            }
            return ResultMessage.err();
        } else {
            return ResultMessage.ok(communityReleaseVo);
        }
    }

    @Override
    public PageUtils getApplyList(Map<String, Object> map) {
        IPage page = new Query().getPage(map);
        String userId = map.get("userId").toString();
        Integer total = releaseSlipDao.getPassAndApplyListTotal(map);
        List<CommunityReleasePassAndApplyVo> list = new ArrayList<>();
        if (total > 0) {
            list = releaseSlipDao.getPassAndApplyList(map);
        }
        page.setTotal(total);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public PageUtils getPassList(Map<String, Object> map) {
        IPage page = new Query().getPage(map);
        map.put("checkResult", ConfigConstant.AUTH);
        Integer total = releaseSlipDao.getPassAndApplyListTotal(map);
        List<CommunityReleasePassAndApplyVo> list = new ArrayList<>();
        if (total > 0) {
            list = releaseSlipDao.getPassAndApplyList(map);
        }
        page.setTotal(total);
        page.setRecords(list);
        return new PageUtils(page);
    }
}
