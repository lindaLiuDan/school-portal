package com.info.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.user.dao.ICommunityUserInfoDao;
import com.info.modules.user.entity.CommunityUserInfoEntity;
import com.info.modules.user.entity.CommunityUserTokenEntity;
import com.info.modules.user.form.LoginForm;
import com.info.modules.user.form.UpdateMobileForm;
import com.info.modules.user.form.UserUpdatePwdForm;
import com.info.modules.user.service.ICommunityUserInfoService;
import com.info.modules.user.service.ICommunityUserTokenService;
import com.info.redis.RedisKeyUtils;
import com.info.string.Md5;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-11 19:01:05
 */
@Service("communityUserInfoService")
public class CommunityUserInfoServiceImpl extends ServiceImpl<ICommunityUserInfoDao, CommunityUserInfoEntity> implements ICommunityUserInfoService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(CommunityUserInfoServiceImpl.class);


    @Autowired
    private ICrudRedisManager<CommunityUserInfoEntity> crudRedisManager;

    @Autowired
    private ICommunityUserTokenService tokenService;

    @Autowired
    private ICommunityUserInfoDao communityUserInfoDao;


    @Override
    public ResultMessage login(LoginForm form) {
        CommunityUserInfoEntity user = findByMobile(form.getMobile());
        if (user == null) {
            return ResultMessage.error("手机号不正确!");
        }
        //判断用户状态是否是删除状态   如果是删除状态不可登陆 会员状态 0 删除 1 正常
        if (user.getIsDel().equals(ConfigConstant.DEL)) {
            return ResultMessage.error("此用户已被删除,暂无法登陆!");
        }
        if (!Md5.md5toUpperCase(form.getPassword()).equals(user.getPwd())) {
            return ResultMessage.error("密码不正确!");
        }
        Map<String, Object> map = new HashMap();
        //每次登录都生成token
        CommunityUserTokenEntity token = tokenService.createToken(user.getUserId());
        map.put("token", token);
        return ResultMessage.ok(map);
    }


    /**
     * @Description 退出登录
     * @Author LiuDan
     * @Date 2019/6/15 10:36
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage logout(Integer userId, HttpServletRequest request) {
        tokenService.expireToken(userId);//使数据库中的token失效
        String token = request.getHeader("token");
        crudRedisManager.delete(RedisKeyUtils.OwnerKeys.TOKEN + userId, "用户退出登录，从redis中删除token异常，Exception{},异常信息为:");
        return ResultMessage.ok();
    }


    /**
     * @Description用户重置密码
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage updatePassword(UserUpdatePwdForm form) {

        CommunityUserInfoEntity user = this.getOne(new QueryWrapper<CommunityUserInfoEntity>().eq("user_id", form.getUserId()));
        if (user == null) {
            return ResultMessage.error("用户不存在");
        }
        //判断传递过来的旧密码和数据库中的密码是否相同   todo  这里转换成MD5大写了 前台不用再转换了
        if (Md5.md5toUpperCase(form.getOldPwd()).equals(user.getPwd())) {
            user.setPwd(Md5.md5toUpperCase(form.getPwd()));
            communityUserInfoDao.update(user, new QueryWrapper<CommunityUserInfoEntity>().eq("mobile", user.getMobile()));
        } else {
            return ResultMessage.error("旧密码不一致");
        }


        return ResultMessage.ok();
    }

    /**
     * @Description 修改绑定手机号
     * @Author LiuDan
     * @Date 2019/6/14 22:51
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage updateMobile(UpdateMobileForm updateMobileForm) {
        boolean mobileIsOrNot = checkMobile(updateMobileForm.getMobile());
        if (mobileIsOrNot == true) {
            return ResultMessage.error("输入的新手机号已注册");
        }
        CommunityUserInfoEntity userInfoEntity = this.getOne(new QueryWrapper<CommunityUserInfoEntity>().eq("user_id", updateMobileForm.getUserId()));
        if (userInfoEntity == null) {
            return ResultMessage.error("用户不存在");
        }
        String oldPhone = userInfoEntity.getMobile();
        userInfoEntity.setMobile(updateMobileForm.getMobile());
        userInfoEntity.setPwd(Md5.md5toUpperCase(updateMobileForm.getNewPwd()));
        boolean flag = this.update(userInfoEntity, new QueryWrapper<CommunityUserInfoEntity>().eq("user_id", updateMobileForm.getUserId()));
        if (flag == false) {
            return ResultMessage.error("ERROR");
        }

        crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.USER_INFO_MOBILE, oldPhone, "redis中删掉旧的电话号码，redis异常,Exception{},异常信息为:");//redis中删掉旧的，存储新的手机号
        crudRedisManager.hset(RedisKeyUtils.OwnerKeys.USER_INFO_MOBILE, updateMobileForm.getMobile(), "1", "向redis中放入新的电话号码redis异常,Exception{},异常信息为:");
        crudRedisManager.delete(RedisKeyUtils.OwnerKeys.TOKEN + updateMobileForm.getUserId(), "修改绑定手机号,从redis中删除token异常，Exception{},异常信息为:");

        //使token失效，让用户重新登录
        tokenService.expireToken(updateMobileForm.getUserId());
        return ResultMessage.ok();
    }

    /**
     * @Description根据mobile 查询用户信息以及判断用户是否存在
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public CommunityUserInfoEntity findByMobile(String mobile) {
        CommunityUserInfoEntity userInfoEntity = this.getOne(new QueryWrapper<CommunityUserInfoEntity>().eq("mobile", mobile));
        return userInfoEntity;
    }


    /**
     * @Description查询电话号码是否注册,true 已经注册过, false 未注册
     * @Author LiuDan
     * @Date 2019/6/15 9:46
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public boolean checkMobile(String mobile) {
        String b = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.USER_INFO_MOBILE, mobile, "从redis中校验手机号码是否已经存在出现异常");
        if (StringUtils.isNotBlank(b)) {
            return true;
        }
        //查询此电话号码是否已经注册过
        CommunityUserInfoEntity entity = this.getOne(new QueryWrapper<CommunityUserInfoEntity>().eq("mobile", mobile));
        if (entity != null) {
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.USER_INFO_MOBILE, mobile, "1", "将用户手机号码放入redis异常");
            return true;
        }
        return false;
    }


}
