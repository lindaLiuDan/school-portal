package com.info.modules.provider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.provider.vo.ProvideMyselfVo;
import com.info.modules.provider.vo.ProviderInfoEntityVO;
import org.apache.ibatis.annotations.Param;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
public interface IProviderInfoDao extends BaseMapper<ProviderInfoEntityVO> {


    /**
     * @Description 根据商户id 查询商户个人信息
     * @Author LiuDan
     * @Date 2019/7/13 15:38
     * @Param
     * @Return
     * @Exception
     */
    ProvideMyselfVo findUserInfoById(@Param("userId") Integer userId);

}
