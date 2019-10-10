package com.info.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.form.UserBaseForm;
import org.apache.ibatis.annotations.Param;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 14:47:56
 */
public interface IUserInfoDao extends BaseMapper<UserInfoEntity> {

    /**
     * @Description 根据用户id查询用户基本信息 ————个人中心展示
     * @Author LiuDan
     * @Date 2019/6/15 11:41
     * @Param
     * @Return
     * @Exception
     */
    UserBaseForm findUserInfoById(@Param("userId") Integer userId);


    /**
     * @Description 根据用户查询该用户所属小区
     * @Author LiuDan
     * @Date 2019/6/25 10:30
     * @Param
     * @Return
     * @Exception
     */
    UserInfoEntity getCommunity(@Param("userId") Integer userId);

}
