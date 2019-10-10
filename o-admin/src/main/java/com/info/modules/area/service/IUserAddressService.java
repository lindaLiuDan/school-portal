package com.info.modules.area.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.area.entity.UserAddressEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-03-07 11:24:39
 */
public interface IUserAddressService extends IService<UserAddressEntity> {

    /**
     * 功能描述: 会员收货地址表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);


    /**
     * 功能描述: APP端查询用户有多少个收货地址
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    Integer selectCount(UserAddressEntity userAddressEntity);

    /**
     * 功能描述: APP端添加用戶收穫地址
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    ResultMessage insertAddress(UserAddressEntity userAddressEntity);

    /**
     * 功能描述: APP端用戶修改收穫地址
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    ResultMessage updateAddressById(UserAddressEntity userAddressEntity);

    /**
     * 功能描述: 根据收货地址ID用户ID查询收货地址详细信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:18
     * @Return:
     */
    UserAddressEntity selectByIdEntity(Integer addressId);

    /**
     * 功能描述: APP端无分页查询我的收货地址的
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:18
     * @Return:
     */
    List<UserAddressEntity> all(Map<String, Object> params);

    /**
     * 功能描述: 删除用户地址详情
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/20 19:49
     * @Return:
     */
    ResultMessage del(Integer addressId);
}

