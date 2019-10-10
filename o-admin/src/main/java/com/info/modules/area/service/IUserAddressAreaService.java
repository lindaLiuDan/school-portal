package com.info.modules.area.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.area.entity.UserAddressAreaEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @ProjectName: gs-info
 * @ClasssName: IApiUserService
 * @Description:
 * @Author: Gaosx 960889426@qq.com By Email
 * @Date: 2018/9/2 8:27
 * @Version: 1.0.0
 **/
public interface IUserAddressAreaService extends IService<UserAddressAreaEntity> {

    /**
     * @Author: Gaosx 960889426@qq.com
     * @Description: 分页查询城市列表
     * @Date: 8:28 2018/9/2
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页查询城市列表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:05
     * @Return:
     */
    List<UserAddressAreaEntity> all(Map<String, Object> params);

    /**
     * 功能描述: 根据areaId查询对应的信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 9:48
     * @Return:
     */
    UserAddressAreaEntity getAreaById(Integer areaId);

    /**
     * 功能描述: 根据areaId查询对应的下一级区域的集合
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 9:50
     * @Return:
     */
    List<UserAddressAreaEntity> getAreaList(Integer areaId);


}
