package com.info.modules.repair.manager.impl;

import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.repair.manager.IRepairRedisManager;
import com.info.manager.CrudRedisManager;
import org.springframework.stereotype.Component;

/**
 * @author 高山溪
 * @Date: 2019/6/14 19:54
 * @Description:
 */
@Component
public class RepairRedisManager extends CrudRedisManager<CommunityInfoEntity> implements IRepairRedisManager {

}
