package com.info.modules.community.manager.impl;

import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.manager.IBuildInfoRedisManager;
import com.info.manager.CrudRedisManager;
import org.springframework.stereotype.Component;

/**
 * @author 高山溪
 * @Date: 2019/6/14 19:54
 * @Description:
 */
@Component
public class BuildInfoRedisManager extends CrudRedisManager<CommunityBuildInfoEntity> implements IBuildInfoRedisManager {

}
