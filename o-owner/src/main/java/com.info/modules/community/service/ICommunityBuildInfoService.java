package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 社区楼房信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 18:36:11
 */
public interface ICommunityBuildInfoService extends IService<CommunityBuildInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区楼房信息表
     * @Date: 2019-06-11 18:36:11
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据楼层号查询对应的房间号 多类型的查询方法
     * 社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号 buildType
     *
     * @Params: * @param null
     * @Author: Gaosx by user
     * @Date: 2019-06-11 18:36:11
     * @Return: infoId 社区ID主键 buildId 楼房信息ID主键 buildType  楼房信息ID主键类型　1 楼号 2 单元号 3 楼层 4 房号
     */
    List<CommunityBuildInfoEntity> all(Map<String, Object> params);

    /**
     * 功能描述: 根据社区ID查询所有的楼号，单元，楼层信息使用redis
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    List<CommunityBuildInfoEntity> getBuildInfo(Integer infoId);

    /**
     * 功能描述: 根据房号查询对应的 楼号 单元号 楼层--这是一个反向查询的方法 根据ID楼号——》楼层--》单元--》楼号--》社区
     *
     * @Params:  * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 17:42
     * @Return:
     */
    CommunityBuildInfoEntity getRoomInfo(Integer buildId);


}

