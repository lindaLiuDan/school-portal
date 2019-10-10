package com.info.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.form.UserAuthForm;
import com.info.manager.ICrudRedisManager;
import com.info.modules.user.dao.IUserAuthDao;
import com.info.modules.user.service.IUserAuthService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
@Service("userAuthService")
public class UserAuthServiceImpl extends ServiceImpl<IUserAuthDao, UserAuthEntity> implements IUserAuthService {


    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private ICrudRedisManager<UserAuthEntity> crudRedisManager;


    /**
     * 功能描述: 会员业主身份认证表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/21 11:44
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserAuthEntity> page = this.page(
                new Query<UserAuthEntity>().getPage(params),
                new QueryWrapper<UserAuthEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 获取用户ID主键获取认证详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/13 17:51
     * @Return:
     */
    @Override
    public UserAuthEntity getUserById(Integer userId) {
        UserAuthEntity userAuthEntity = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.AUTH_INFO, userId.toString(), UserAuthEntity.class, "查询用户认证信息Redis异常,Exception{},异常信息为:");
        if (userAuthEntity == null) {
            userAuthEntity = this.getOne(new QueryWrapper<UserAuthEntity>()
                    .eq("user_id", userId)
            );
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.AUTH_INFO, userId.toString(), JSON.toJSONString(userAuthEntity), "存储用户认证信息Redis异常,Exception{},异常信息为:");
        }
        return userAuthEntity;
    }

    /**
     * 功能描述: 根据用户ID主键修改认证信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/14 11:18
     * @Return:
     */
    @Override
    public ResultMessage updateUserById(UserAuthEntity userAuthEntity) {
        Boolean flag = this.updateById(userAuthEntity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.AUTH_INFO, userAuthEntity.getUserId().toString(), "查询用户认证信息详情Redis异常,Exception{},异常信息为:");
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * @Description 业主身份认证————app
     * @Author LiuDan
     * @Date 2019/6/15 19:06
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage addAuth(UserAuthForm form) {
        Integer userId = form.getUserId();
        //查询该用户是否已经实名认证
        UserAuthEntity authEntity = this.getUserById(userId);
        UserAuthEntity entity = new UserAuthEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setCreatorTime(DateUtils.now());
        boolean save;
        if (authEntity == null) {
            save = this.save(entity);
        } else {
            entity.setAuthId(authEntity.getAuthId());
            //未认证
            entity.setIsAuth(ConfigConstant.DEL);
            save = this.updateById(entity);
        }
        if (save) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * @Description 重新认证_根据id 查询出来信息
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage getAuthById(Integer authId) {
        UserAuthEntity entity = this.getById(authId);
        if (entity != null) {
            return ResultMessage.ok(entity);
        }
        return ResultMessage.err();
    }

    /**
     * @Description 重新认证_根据id 查询出来信息
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage updateAuth(UserAuthForm form) {
        UserAuthEntity entity = new UserAuthEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setEditorTime(DateUtils.now());
        entity.setIsAuth(ConfigConstant.DEL);
        Boolean flag = this.updateById(entity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.OwnerKeys.AUTH_INFO, form.getUserId().toString(), "查询用户认证信息Redis异常,Exception{},异常信息为:");
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

}
