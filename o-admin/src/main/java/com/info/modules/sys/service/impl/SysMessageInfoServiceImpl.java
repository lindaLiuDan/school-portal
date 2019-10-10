package com.info.modules.sys.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.sys.dao.ISysMessageInfoDao;
import com.info.modules.sys.entity.SysMessageInfoEntity;
import com.info.modules.sys.service.ISysMessageInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 系统消息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-16 15:01:31
 */
@Service("sysMessageInfoService")
public class SysMessageInfoServiceImpl extends ServiceImpl<ISysMessageInfoDao, SysMessageInfoEntity> implements ISysMessageInfoService {

    @Autowired
    private ICrudRedisManager<SysMessageInfoEntity> crudRedisManager;


    /**
     * 功能描述: 系统消息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-16 15:01:31
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<SysMessageInfoEntity> page = this.page(
                new Query<SysMessageInfoEntity>().getPage(params),
                new QueryWrapper<SysMessageInfoEntity>()
                        .select("message_id,title,content,account_id,info_id,creator_time,is_read")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 查看详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/16 18:12
     * @Return:
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public SysMessageInfoEntity getMessageInfoById(Integer messageId) {
        SysMessageInfoEntity sysMessageInfoEntity = crudRedisManager.hget(RedisKeyUtils.MessageKeys.MESSAGE_INFO, messageId.toString(), SysMessageInfoEntity.class, "获取SystemModuleKyes.MESSAGE_INFO，id=" + messageId + "异常：");
        if (sysMessageInfoEntity == null) {
            sysMessageInfoEntity = this.getById(messageId);
            if (sysMessageInfoEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.MessageKeys.MESSAGE_INFO, messageId.toString(), JSON.toJSONString(sysMessageInfoEntity), "存储SystemModuleKyes.MESSAGE_INFO，id=" + messageId + "异常：");
                //标记为已读信息
                this.messageInfoRead(messageId);
                return sysMessageInfoEntity;
            }
        }
        return sysMessageInfoEntity;
    }

    /**
     * 功能描述: 标记为已读信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/16 18:25
     * @Return:
     */
    public void messageInfoRead(Integer messageId) {
        SysMessageInfoEntity sysMessageInfoEntity = new SysMessageInfoEntity();
        sysMessageInfoEntity.setMessageId(messageId);
        sysMessageInfoEntity.setIsRead(Constant.IsRead.IS_READ.getValue());
        this.updateById(sysMessageInfoEntity);

    }
}
