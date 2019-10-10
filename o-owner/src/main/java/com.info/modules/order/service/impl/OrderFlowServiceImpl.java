package com.info.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.order.entity.OrderFlowEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.order.dao.IOrderFlowDao;
import com.info.modules.order.service.IOrderFlowService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单流程跟踪表:已支付，未支付，配送中等
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Service("orderFlowService")
public class OrderFlowServiceImpl extends ServiceImpl<IOrderFlowDao, OrderFlowEntity> implements IOrderFlowService {


    @Autowired
    private ICrudRedisManager<OrderFlowEntity> crudRedisManager;


    /**
     * 功能描述: 订单流程跟踪表:已支付，未支付，配送中等
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:29
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OrderFlowEntity> page = this.page(
                new Query<OrderFlowEntity>().getPage(params),
                new QueryWrapper<OrderFlowEntity>()
                        .select("open_id,city_name,city_no,parent_id")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 查询所有的订单流程
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 11:41
     * @Return:
     */
    @Override
    public List<OrderFlowEntity> AllFlow(Map<String, Object> params) {
        String listFlow = crudRedisManager.get(RedisKeyUtils.OrderKeys.ORDER_FLOW_LIST, "查询订单流程集合,Redis异常,Exception{},异常信息为:");
        if (listFlow == null) {
            List<OrderFlowEntity> list = this.list(new QueryWrapper<OrderFlowEntity>()
                    .select("flow_id,type_num,flow_name,detail")
            );
            crudRedisManager.set(RedisKeyUtils.OrderKeys.ORDER_FLOW_LIST, JSON.toJSONString(listFlow), "存储订单流程集合,Redis异常,Exception{},异常信息为:");
            return list;
        } else {
            return JSON.parseArray(listFlow, OrderFlowEntity.class);
        }
    }

    /**
     * 功能描述: 获取单个流程信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 11:56
     * @Return:
     */
    @Override
    public OrderFlowEntity getFlowById(Integer flowId) {
        OrderFlowEntity orderFlow = crudRedisManager.hget(RedisKeyUtils.OrderKeys.ORDER_FLOW_INFO, flowId.toString(), OrderFlowEntity.class, "查询单个订单流程,Redis异常,Exception{},异常信息为:");
        if (orderFlow == null) {
            orderFlow = this.getById(flowId);
            crudRedisManager.hset(RedisKeyUtils.OrderKeys.ORDER_FLOW_INFO, flowId.toString(), JSON.toJSONString(orderFlow), "查询单个订单流程,Redis异常,Exception{},异常信息为:");
        }
        return orderFlow;
    }

}
