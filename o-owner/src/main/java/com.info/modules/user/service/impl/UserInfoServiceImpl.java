package com.info.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.community.vo.CommunityInfoVo;
import com.info.modules.user.entity.TokenEntity;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.form.*;
import com.info.img.ThumbnailatorUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.dao.IUserInfoDao;
import com.info.modules.token.service.ITokenService;
import com.info.modules.user.service.IUserInfoService;
import com.info.modules.user.service.ImgService;
import com.info.redis.RedisKeyUtils;
import com.info.string.Md5;
import com.info.string.UUIDUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<IUserInfoDao, UserInfoEntity> implements IUserInfoService {


    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    @Autowired
    private ICrudRedisManager<UserInfoEntity> crudRedisManager;

    @Autowired
    private ICodeService sysCodeService;

    @Autowired
    private ITokenService ITokenService;

    @Autowired
    private IUserInfoDao userInfoDao;

    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    @Autowired
    private ImgService imgService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 会员业主信息表
     * @Date: 2019-06-14 14:03:54
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserInfoEntity> page = this.page(
                new Query<UserInfoEntity>().getPage(params),
                new QueryWrapper<UserInfoEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * @Description 用户注册
     * @Author LiuDan
     * @Date 2019/6/13 12:12
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage regUser(UserRegistForm form) {

        //1，已注册的情况
        if (checkMobile(form.getMobile())) {
            boolean b = sysCodeService.checkCode(form.getMobile(), form.getCheckCode());
            if (!b) {
                return ResultMessage.error("验证码错误");
            }
            Map map = new HashMap();
            UserInfoEntity entity = this.getOne(new QueryWrapper<UserInfoEntity>().eq("mobile", form.getMobile()));
            TokenEntity token = ITokenService.createToken(entity.getUserId());
            map.put("token", token);
            //成功后删除redis中的验证码
            crudRedisManager.delete(form.getMobile(), "登录成功后删除redis中的验证码，Redis异常,Exception{},异常信息为:");
            return ResultMessage.ok(map);
        }
        //2，未注册的
        boolean b = sysCodeService.checkCode(form.getMobile(), form.getCheckCode());
        if (!b) {
            return ResultMessage.error("验证码错误");
        }

        UserInfoEntity userEntity = new UserInfoEntity();
        BeanUtils.copyProperties(form, userEntity);
        String random = UUIDUtils.getUUID32().toString().substring(0, 10);
        userEntity.setUserName(random);//随机字符串作为默认用户名
        userEntity.setRegTime(DateUtils.now());
        boolean save = this.save(userEntity);
        if (!save) {
            return ResultMessage.error("登录失败");
        }
        Map map = new HashMap();
        //redis存入手机号
        crudRedisManager.hset(RedisKeyUtils.UserInfoKyes.USER_INFO_MOBILE, userEntity.getMobile(), "1", "登录成功后向redis中存入手机号码，redis异常,Exception{}，异常信息为：");
        //注册成功后删除redis中的验证码
        crudRedisManager.delete(form.getMobile(), "登录成功后删除redis中的验证码，Redis异常,Exception{},异常信息为:");
        TokenEntity token = ITokenService.createToken(userEntity.getUserId());
        map.put("token", token);
        return ResultMessage.ok(map);
    }

    @Override
    public ResultMessage login(LoginForm form) {
        UserInfoEntity user = findByMobile(form.getMobile());
        if (user == null) {
            return ResultMessage.error("手机号不正确!");
        }
        //判断用户状态是否是冻结状态   如果是冻结状态不可登陆 会员状态 0 冻结 1 正常
        if (user.getFrozen().equals(ConfigConstant.DEL)) {
            return ResultMessage.error("此用户已被冻结,暂无法登陆!");
        }
        if (!!Md5.md5toUpperCase(form.getPassword()).equals(user.getPwd())) {
            return ResultMessage.error("密码不正确!");
        }
        Map<String, Object> map = new HashMap();
        //每次登录都生成token
        TokenEntity token = ITokenService.createToken(user.getUserId());
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
        ITokenService.expireToken(userId);//使数据库中的token失效
        String token = request.getHeader("token");
        crudRedisManager.delete(RedisKeyUtils.UserInfoKyes.TOKEN + userId, "用户退出登录，从redis中删除token异常，Exception{},异常信息为:");
        return ResultMessage.ok();
    }

    /**
     * @Description 添加登录密码——app
     * @Author LiuDan
     * @Date 2019/6/21 11:49
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage addPsw(Integer userId, String password) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setUserId(userId);
        //MD5转大写
        entity.setPwd(Md5.md5toUpperCase(password));
        boolean b = this.updateById(entity);
        if (b) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.error();
        }
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
        UserInfoEntity user = this.getOne(new QueryWrapper<UserInfoEntity>().eq("user_id", form.getUserId()));
        if (user == null) {
            return ResultMessage.error("用户不存在");
        }
        //判断传递过来的旧密码和数据库中的密码是否相同   todo  这里转换成MD5大写了 前台不用再转换了
        if (Md5.md5toUpperCase(form.getOldPwd()).equals(user.getPwd())) {
            user.setPwd(Md5.md5toUpperCase(form.getPwd()));
            userInfoDao.update(user, new QueryWrapper<UserInfoEntity>().eq("mobile", user.getMobile()));
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
        UserInfoEntity userInfoEntity = this.getOne(new QueryWrapper<UserInfoEntity>().eq("user_id", updateMobileForm.getUserId()));
        if (userInfoEntity == null) {
            return ResultMessage.error("用户不存在");
        }
        String oldPhone = userInfoEntity.getMobile();
        userInfoEntity.setMobile(updateMobileForm.getMobile());
        userInfoEntity.setPwd(Md5.md5toUpperCase(updateMobileForm.getNewPwd()));
        boolean flag = this.update(userInfoEntity, new QueryWrapper<UserInfoEntity>().eq("user_id", updateMobileForm.getUserId()));
        if (flag == false) {
            return ResultMessage.error("ERROR");
        }

        crudRedisManager.hdel(RedisKeyUtils.UserInfoKyes.USER_INFO_MOBILE, oldPhone, "redis中删掉旧的电话号码，redis异常,Exception{},异常信息为:");//redis中删掉旧的，存储新的手机号
        crudRedisManager.hset(RedisKeyUtils.UserInfoKyes.USER_INFO_MOBILE, updateMobileForm.getMobile(), "1", "向redis中放入新的电话号码redis异常,Exception{},异常信息为:");
        crudRedisManager.delete(RedisKeyUtils.UserInfoKyes.TOKEN + updateMobileForm.getUserId(), "修改绑定手机号,从redis中删除token异常，Exception{},异常信息为:");

        //使token失效，让用户重新登录
        ITokenService.expireToken(updateMobileForm.getUserId());
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
    public UserInfoEntity findByMobile(String mobile) {
        UserInfoEntity userInfoEntity = this.getOne(new QueryWrapper<UserInfoEntity>().eq("mobile", mobile));
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
        String b = crudRedisManager.hget(RedisKeyUtils.UserInfoKyes.USER_INFO_MOBILE, mobile, "从redis中校验手机号码是否已经存在出现异常");
        if (StringUtils.isNotBlank(b)) {
            return true;
        }
        //查询此电话号码是否已经注册过
        UserInfoEntity entity = this.getOne(new QueryWrapper<UserInfoEntity>().eq("mobile", mobile));
        if (entity != null) {
            crudRedisManager.hset(RedisKeyUtils.UserInfoKyes.USER_INFO_MOBILE, mobile, "1", "将用户手机号码放入redis异常");
            return true;
        }
        return false;
    }

    /**
     * @param
     * @return
     * @description: 查询个人信息
     * @author liudan
     * @date 2019/3/23 13:54
     */
    @Override
    public ResultMessage findUserInfoById(Integer userId) {
        UserBaseForm form = userInfoDao.findUserInfoById(userId);
        return ResultMessage.ok(form);
    }

    @Override
    public Boolean updateUser(UserUpdateForm form) {
        UserInfoEntity entity = this.getById(form.getUserId());
        String smallImg = null;
        String img1 = null;
        if (form.getImg() != null) {
            try {
                Map<String, Object> stringObjectMap = thumbnailatorUtils.uploadFile(form.getImg());
                Map<String, Object> map = imgService.uploadFile(stringObjectMap);
                smallImg = map.get("smallImgUrl").toString();
                img1 = map.get("bigImgUrl").toString();
                entity.setImg(img1);
                entity.setSmallImg(smallImg);
            } catch (Exception e) {
                logger.error("更新用户头像上传图片异常 ,用户id:{}, 异常信息为: ", form.getUserId(), e);
            }
        }
        if (StringUtils.isNotBlank(form.getUserName())) {
            entity.setUserName(form.getUserName());
        }
        if (StringUtils.isNotBlank(form.getSex().toString())) {
            entity.setSex(form.getSex());
        }
        return this.updateById(entity);
    }

    /**
     * @Description 根据用户查询该用户所属小区
     * @Author LiuDan
     * @Date 2019/6/25 10:30
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage getCommunity(Integer userId) {
        CommunityInfoVo community = crudRedisManager.hget(RedisKeyUtils.UserInfoKyes.USER_COMMUNITY, userId.toString(), CommunityInfoVo.class, "从redis中获取用户所在小区失败,Exception{},异常信息为：");
        if (community == null) {
            UserInfoEntity userInfoEntity = userInfoDao.getCommunity(userId);
            CommunityInfoVo communityInfoEntity = new CommunityInfoVo();
            communityInfoEntity.setInfoName(userInfoEntity.getInfoName());
            communityInfoEntity.setInfoId(userInfoEntity.getInfoId());
            crudRedisManager.hset(RedisKeyUtils.UserInfoKyes.USER_COMMUNITY, userId.toString(), JSON.toJSONString(communityInfoEntity), "向redis中放入新的电话号码redis异常,Exception{},异常信息为:");
            return ResultMessage.ok(communityInfoEntity);
        }
        return ResultMessage.ok(community);
    }
}
