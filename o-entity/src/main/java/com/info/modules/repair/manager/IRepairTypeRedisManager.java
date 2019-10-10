package com.info.modules.repair.manager;

import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.manager.ICrudRedisManager;

import java.util.List;

/**
 * @author 高山溪
 * @Auther: 高山溪
 * @Date: 2019/6/14 19:52
 * @Description:
 */
public interface IRepairTypeRedisManager extends ICrudRedisManager<CommunityRepairTypeEntity> {



    /**
     * 功能描述: 获取所有类别的集合
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:25
     * @Return:
     */
    List<CommunityRepairTypeEntity> RepairType(String key);

    /**
     * 功能描述: 循环向list中存储集合对象
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:50
     * @Return:
     */
    void setRepairType(String key, List<CommunityRepairTypeEntity> list);

}
