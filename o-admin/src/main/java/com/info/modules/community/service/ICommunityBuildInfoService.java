package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

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
     * 功能描述: 添加楼房信息--符复合添加接口
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 15:33
     * @Return:
     */
    ResultMessage addBuildInfo(CommunityBuildInfoEntity communityBuildInfoEntity);

    /**
     * 功能描述: 修改楼房信息--符复合添加接口
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 15:46
     * @Return:
     */
    ResultMessage updateBuildInfo(CommunityBuildInfoEntity communityBuildInfoEntity);

    /**
     * 功能描述: 删除楼房信息--有子类的话不允许删除
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 15:56
     * @Return:
     */
    ResultMessage delBuildInfo(Integer buildId,Integer buildType);
}

