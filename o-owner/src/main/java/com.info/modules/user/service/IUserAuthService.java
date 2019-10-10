package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.form.UserAuthForm;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
public interface IUserAuthService extends IService<UserAuthEntity> {

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 会员业主身份认证表
     * @Date: 2019-06-14 14:03:54
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 获取用户ID主键获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/13 17:50
     * @Return:
     */
    UserAuthEntity getUserById(Integer userId);

    /**
     * 功能描述: 根据用户ID主键修改认证信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 11:18
     * @Return:
     */
    ResultMessage updateUserById(UserAuthEntity userAuthEntity);

    /**
     * @Description 业主身份认证————app
     * @Author LiuDan
     * @Date 2019/6/15 19:06
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage addAuth(UserAuthForm form);

    /**
     * @Description 重新认证_根据id 查询出来信息
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage getAuthById(Integer authId);

    /**
     * @Description 重新认证_根据id 查询出来信息
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage updateAuth(UserAuthForm form);
}

