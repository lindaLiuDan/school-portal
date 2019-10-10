package com.info.modules.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.date.DateUtils;
import com.info.img.ThumbnailatorUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.provider.dao.IProviderInfoDao;
import com.info.modules.provider.entity.ProviderInfoTokenEntity;
import com.info.modules.provider.service.IProviderGradeService;
import com.info.modules.provider.service.IProviderInfoService;
import com.info.modules.provider.service.IProviderInfoTokenService;
import com.info.modules.provider.service.IProviderTypeService;
import com.info.modules.provider.vo.ProvideMyselfVo;
import com.info.modules.provider.vo.ProviderInfoEntityVO;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.form.*;
import com.info.modules.user.service.ImgService;
import com.info.redis.RedisKeyUtils;
import com.info.string.Md5;
import com.info.string.UUIDUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Service("providerInfoService")
public class ProviderInfoServiceImpl extends ServiceImpl<IProviderInfoDao, ProviderInfoEntityVO> implements IProviderInfoService {


    private static final Logger logger = LoggerFactory.getLogger(ProviderInfoServiceImpl.class);

    @Autowired
    private IProviderGradeService gradeService;

    @Autowired
    private IProviderTypeService providerTypeService;

    @Autowired
    private ICrudRedisManager<ProviderInfoEntityVO> crudRedisManager;

    @Autowired
    private ICodeService sysCodeService;

    @Autowired
    private IProviderInfoTokenService ITokenService;

    @Autowired
    private IProviderInfoDao providerInfoDao;


    @Autowired
    private ThumbnailatorUtils thumbnailatorUtils;

    @Autowired
    private ImgService imgService;


    /**
     * 功能描述: 商家信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String providerName = (String) params.get("providerName");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProviderInfoEntityVO> page = this.page(
                new Query<ProviderInfoEntityVO>().getPage(params),
                new QueryWrapper<ProviderInfoEntityVO>()
                        .select("provider_id,info_id,grade_id,provider_name,provider_no,logo,small_logo,memo,start_price,shipp_fee,longitude,dimension,status")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .like(StringUtils.isNotBlank(providerName), "provider_name", providerName)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        page.getRecords().stream().forEach(info -> {
            info.setTypeName(providerTypeService.getProviderTypeById(info.getTypeId()).getTypeName());
        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 获取店铺信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 18:13
     * @Return:
     */
    @Override
    public ProviderInfoEntityVO getProviderInfoById(Integer providerId) {
        ProviderInfoEntityVO info = crudRedisManager.hget(RedisKeyUtils.ProviderKeys.PROVIDER_INFO, providerId.toString(), ProviderInfoEntityVO.class, "获取单个店铺信息详情,Redis异常,Exception{},异常信息为");
        if (info == null) {
            info = this.getById(providerId);
            if (info != null) {
                //由于设计上的变动商家所属分类不再有了，所有去掉该方法
//                info.setTypeName(providerTypeService.getProviderTypeById(info.getTypeId()).getTypeName());
                info.setGname(gradeService.getProviderGradeById(info.getGradeId()).getGname());
            }
            crudRedisManager.hset(RedisKeyUtils.ProviderKeys.PROVIDER_INFO, providerId.toString(), JSON.toJSONString(info), "存储单个店铺信息详情,Redis异常,Exception{},异常信息为");
            return info;
        }
        return info;
    }

    /**
     * 功能描述: 根据店铺类型主键查询店铺信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 11:41
     * @Return:
     */
    @Override
    public PageUtils typeList(Integer providerId) {

        return null;
    }

    /**
     * 功能描述: 修改店铺信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/13 11:21
     * @Return:
     */
    @Override
    public ResultMessage saveProviderInfo(ProviderInfoEntityVO providerInfoEntityVO, MultipartFile[] file) {



        return null;
    }

    /**
     * @Description 根据验证码登录————商户端
     * @Author LiuDan
     * @Date 2019/7/12 21:08
     * @Param
     * @Return
     * @Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMessage loginCode(UserRegistForm registForm) {
        //已经注册过
        if (checkMobile(registForm.getMobile())) {
            boolean b = sysCodeService.checkCode(registForm.getMobile(), registForm.getCheckCode());
            if (!b) {
                return ResultMessage.error("验证码错误");
            }
            Map map = new HashMap();
            ProviderInfoEntityVO entity = this.getOne(new QueryWrapper<ProviderInfoEntityVO>().eq("mobile", registForm.getMobile()));
            ProviderInfoTokenEntity token = ITokenService.createToken(entity.getProviderId());
            map.put("token", token);
            //成功后删除redis中的验证码
            crudRedisManager.delete(registForm.getMobile(), "登录成功后删除redis中的验证码，Redis异常,Exception{},异常信息为:");
            return ResultMessage.ok(map);
        }

        //没有注册的需要添加

        boolean b = sysCodeService.checkCode(registForm.getMobile(), registForm.getCheckCode());
        if (!b) {
            return ResultMessage.error("验证码错误");
        }


        ProviderInfoEntityVO userEntity = new ProviderInfoEntityVO();
        userEntity.setMobile(registForm.getMobile());
        userEntity.setCreatorTime(DateUtils.now());
        String random = UUIDUtils.getUUID32().toString().substring(0, 10);
        userEntity.setUserName(random);
        boolean save = this.save(userEntity);
        if (!save) {
            return ResultMessage.error("登录失败");
        }
        Map map = new HashMap();
        //redis存入手机号
        crudRedisManager.hset(RedisKeyUtils.ProviderKeys.USER_INFO_MOBILE, userEntity.getMobile(), "1", "登录成功后向redis中存入手机号码，redis异常,Exception{}，异常信息为：");
        //注册成功后删除redis中的验证码
        crudRedisManager.delete(registForm.getMobile(), "登录成功后删除redis中的验证码，Redis异常,Exception{},异常信息为:");
        ProviderInfoTokenEntity token = ITokenService.createToken(userEntity.getProviderId());
        map.put("token", token);
        return ResultMessage.ok(map);
    }


    /**
     * @Description 商户账号密码登录
     * @Author LiuDan
     * @Date 2019/7/13 14:37
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage login(LoginForm form) {
        ProviderInfoEntityVO user = findByMobile(form.getMobile());
        if (user == null) {
            return ResultMessage.error("手机号不正确!");
        }
        //判断用户状态是否是冻结状态   如果是冻结状态不可登陆 会员状态 0 冻结 1 正常
        if (user.getIsDel().equals(ConfigConstant.DEL)) {
            return ResultMessage.error("此用户已被冻结,暂无法登陆!");
        }
        if (!Md5.md5toUpperCase(form.getPassword()).equals(user.getPwd())) {
            return ResultMessage.error("密码不正确!");
        }
        Map<String, Object> map = new HashMap();
        //每次登录都生成token
        ProviderInfoTokenEntity token = ITokenService.createToken(user.getProviderId());
        map.put("token", token);
        return ResultMessage.ok(map);
    }


    /**
     * @Description 根据mobile查询用户信息
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ProviderInfoEntityVO findByMobile(String mobile) {
        ProviderInfoEntityVO entityVo = this.getOne(new QueryWrapper<ProviderInfoEntityVO>().eq("mobile", mobile));
        return entityVo;
    }


    /**
     * @Description 退出登录
     * @Author LiuDan
     * @Date 2019/6/15 10:35
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage logout(Integer userId, HttpServletRequest request) {
        ITokenService.expireToken(userId);//使数据库中的token失效
        String token = request.getHeader("token");
        crudRedisManager.delete(RedisKeyUtils.ProviderKeys.TOKEN + userId, "用户退出登录，从redis中删除token异常，Exception{},异常信息为:");
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
        ProviderInfoEntityVO entity = new ProviderInfoEntityVO();
        entity.setProviderId(userId);
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
     * @Description 用户重置密码
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage updatePassword(UserUpdatePwdForm form) {
        ProviderInfoEntityVO user = this.getOne(new QueryWrapper<ProviderInfoEntityVO>().eq("provider_id", form.getUserId()));
        if (user == null) {
            return ResultMessage.error("用户不存在");
        }
        //判断传递过来的旧密码和数据库中的密码是否相同   todo  这里转换成MD5大写了 前台不用再转换了
        if (Md5.md5toUpperCase(form.getOldPwd()).equals(user.getPwd())) {
            user.setPwd(Md5.md5toUpperCase(form.getPwd()));
            providerInfoDao.update(user, new QueryWrapper<ProviderInfoEntityVO>().eq("mobile", user.getMobile()));
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
        ProviderInfoEntityVO userInfoEntity = this.getOne(new QueryWrapper<ProviderInfoEntityVO>().eq("provider_id", updateMobileForm.getUserId()));
        if (userInfoEntity == null) {
            return ResultMessage.error("用户不存在");
        }
        String oldPhone = userInfoEntity.getMobile();
        userInfoEntity.setMobile(updateMobileForm.getMobile());
        userInfoEntity.setPwd(Md5.md5toUpperCase(updateMobileForm.getNewPwd()));
        boolean flag = this.update(userInfoEntity, new QueryWrapper<ProviderInfoEntityVO>().eq("provider_id", updateMobileForm.getUserId()));
        if (flag == false) {
            return ResultMessage.error("ERROR");
        }

        crudRedisManager.hdel(RedisKeyUtils.ProviderKeys.USER_INFO_MOBILE, oldPhone, "redis中删掉旧的电话号码，redis异常,Exception{},异常信息为:");//redis中删掉旧的，存储新的手机号
        crudRedisManager.hset(RedisKeyUtils.ProviderKeys.USER_INFO_MOBILE, updateMobileForm.getMobile(), "1", "向redis中放入新的电话号码redis异常,Exception{},异常信息为:");
        crudRedisManager.delete(RedisKeyUtils.ProviderKeys.TOKEN + updateMobileForm.getUserId(), "修改绑定手机号,从redis中删除token异常，Exception{},异常信息为:");

        //使token失效，让用户重新登录
        ITokenService.expireToken(updateMobileForm.getUserId());
        return ResultMessage.ok();
    }

    /**
     * @Description 根据电话号码查询电话是非存在
     * @Author LiuDan
     * @Date 2019/7/13 16:44
     * @Param
     * @Return
     * @Exception
     */
    public boolean checkMobile(String mobile) {
        String b = crudRedisManager.hget(RedisKeyUtils.ProviderKeys.USER_INFO_MOBILE, mobile, "从redis中校验手机号码是否已经存在出现异常");
        if (StringUtils.isNotBlank(b)) {
            return true;
        }
        //查询此电话号码是否已经注册过
        ProviderInfoEntityVO entity = this.getOne(new QueryWrapper<ProviderInfoEntityVO>().eq("mobile", mobile));
        if (entity != null) {
            crudRedisManager.hset(RedisKeyUtils.ProviderKeys.USER_INFO_MOBILE, mobile, "1", "将用户手机号码放入redis异常");
            return true;
        }
        return false;
    }


    /**
     * @Description 查询商家信息
     * @Author LiuDan
     * @Date 2019/7/13 15:31
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage findUserInfoById(Integer userId) {
        ProvideMyselfVo form = providerInfoDao.findUserInfoById(userId);
        return ResultMessage.ok(form);
    }


    /**
     * @Description 更新商家基本信息
     * @Author LiuDan
     * @Date 2019/7/13 15:31
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public Boolean updateUser(UserUpdateForm form) {
        ProviderInfoEntityVO entity = this.getById(form.getUserId());
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
        return this.updateById(entity);
    }

}
