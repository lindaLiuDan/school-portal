package com.info.modules.advertis.manager.impl;

import com.info.manager.CrudRedisManager;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.modules.advertis.manager.IAdvertisInfoRedisManager;
import org.springframework.stereotype.Component;

/**
 * @author 高山溪
 * @Date: 2019/6/14 19:54
 * @Description:
 */
@Component
public class AdvertisInfoRedisManager extends CrudRedisManager<AdvertisInfoEntity> implements IAdvertisInfoRedisManager {

}
