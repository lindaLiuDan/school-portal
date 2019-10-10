package com.info.modules.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.entity.CommunityPassInfoEntity;
import com.info.modules.community.form.CommunityPassInfoForm;
import com.info.modules.community.dao.ICommunityPassInfoDao;
import com.info.modules.community.service.ICommunityPassInfoService;
import com.info.modules.community.vo.CommunityRedisVo;
import com.info.modules.user.form.SendSMSForm;
import com.info.modules.user.service.ISendService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
@Service("communityPassInfoService")
public class CommunityPassInfoServiceImpl extends ServiceImpl<ICommunityPassInfoDao, CommunityPassInfoEntity> implements ICommunityPassInfoService {


    @Autowired
    private ISendService sendService;

    @Autowired
    private ICrudRedisManager<CommunityPassInfoEntity> crudRedisManager;

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 社区访客通行证信息表
     * @Date: 2019-06-25 14:38:33
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        IPage<CommunityPassInfoEntity> page = this.page(
                new Query<CommunityPassInfoEntity>().getPage(params),
                new QueryWrapper<CommunityPassInfoEntity>().eq("user_id", userId)
        );
        return new PageUtils(page);
    }


    /**
     * @Description 添加访客同行——app
     * @Author LiuDan
     * @Date 2019/6/25 14:52
     * @Param
     * @Return
     * @Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMessage savePassInfo(CommunityPassInfoForm form) {
        SendSMSForm sendSMSForm = new SendSMSForm();
        sendSMSForm.setMd(form.getMd());
        sendSMSForm.setMobile(form.getMobile());
        sendSMSForm.setTimes(form.getTimes());
        sendSMSForm.setTypeId(ConfigConstant.SEND_TYPE_PASS);
        sendSMSForm.setUserId(form.getUserId());
        Date now = DateUtils.now();
        Date date = DateUtils.addDateHours(now, 6);
        sendSMSForm.setStartTime(now);
        sendSMSForm.setEndTime(date);
        //发送验证码
        ResultMessage send = sendService.send(sendSMSForm);
        String code = send.get("code").toString();
        if (code.equals(String.valueOf(ConfigConstant.NOTDEL))) {
            String numbers = send.get("result").toString();
            CommunityPassInfoEntity entity = new CommunityPassInfoEntity();
            entity.setCreatorTime(DateUtils.now());
            entity.setInfoId(form.getInfoId());
            entity.setMobile(form.getMobile());
            entity.setUserId(form.getUserId());
            entity.setCheckCode(Integer.valueOf(numbers));
            entity.setExpireTime(date);
            entity.setBuildId(form.getBuildId());

            boolean b = this.save(entity);
            if (b) {
                //验证码和校验时间需要存到redis中
                String key = RedisKeyUtils.CommunityReleaseSlip.MOBILE + numbers;
                CommunityRedisVo redisVo = new CommunityRedisVo();
                redisVo.setCode(numbers);
                redisVo.setEndTime(date);
                redisVo.setMobile(form.getMobile());
                crudRedisManager.set(key, JSON.toJSONString(redisVo), 60 * 60 * 60 * 6, "访客通行证的验证码放入redis失败，Redis异常,Exception{},异常信息为：");

                return ResultMessage.ok();
            }
        }
        return send;
    }

    /**
     * @Description 根据当前手机号码和当前时间判断此电话号码是否已经过期
     * @Author LiuDan
     * @Date 2019/7/7 14:28
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public Boolean getEntityByMobile(String mobile, Date date) {
        CommunityPassInfoEntity entity = this.getOne(new QueryWrapper<CommunityPassInfoEntity>().eq("mobile", mobile).ge("expire_time", date));
        if (entity != null) {
            return true;
        } else {
            return false;
        }
    }

}
