package com.info.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.order.entity.OrderInfoLogEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.order.dao.IOrderInfoLogDao;
import com.info.modules.order.service.IOrderInfoLogService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 订单日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Service("orderInfoLogService")
public class OrderInfoLogServiceImpl extends ServiceImpl<IOrderInfoLogDao, OrderInfoLogEntity> implements IOrderInfoLogService {


    @Autowired
    private ICrudRedisManager<OrderInfoLogEntity> crudRedisManager;


    /**
     * 功能描述: 订单日志信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:43
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String orderId = (String) params.get("orderId");
        String orderNo = (String) params.get("orderNo");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OrderInfoLogEntity> page = this.page(
                new Query<OrderInfoLogEntity>().getPage(params),
                new QueryWrapper<OrderInfoLogEntity>()
                        .select("log_id,order_id,order_no,total_money,memo,creator_time,creator")
                        .eq(StringUtils.isNotBlank(orderId), "order_id", orderId)
                        .eq(StringUtils.isNotBlank(orderNo), "order_no", orderNo)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页查询所有该订单的日志
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:42
     * @Return:
     */
    @Override
    public List<OrderInfoLogEntity> allLog(Integer orderId) {
        return this.list(new QueryWrapper<OrderInfoLogEntity>()
                .select("log_id,order_id,order_no,total_money,memo,creator_time,creator")
                .eq("order_id", orderId)
        );
//        String logList = crudRedisManager.hget(RedisKeyUtils.OrderKeys.ORDER_LOG, orderId.toString(), "查询订单日志的集合,Redis异常,Exception{},异常信息为:");
//        if (logList == null) {
//            List<OrderInfoLogEntity> list = this.list(new QueryWrapper<OrderInfoLogEntity>()
//                    .select("log_id,order_id,order_no,total_money,memo,creator_time,creator")
//                    .eq("order_id", orderId)
//            );
//            crudRedisManager.hset(RedisKeyUtils.OrderKeys.ORDER_LOG, orderId.toString(), JSON.toJSONString(list), "存储订单日志,Redis异常,Exception{},异常信息为:");
//            return list;
//        } else {
//            return JSON.parseArray(logList, OrderInfoLogEntity.class);
//        }
    }

    /**
     * 功能描述: 添加日志信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:56
     * @Return:
     */
    @Override
    public Boolean addLog(OrderInfoLogEntity orderInfoLogEntity) {
//        Long flag = this.hdel(orderInfoLogEntity.getOrderId());
        Boolean flag = this.save(orderInfoLogEntity);
        if (flag) {
//            if (flag <= 0) {
//                this.hdel(orderInfoLogEntity.getOrderId());
//            }
            return flag;
        } else {
            return flag;
        }
    }

    /**
     * 功能描述: 删除缓存中日志信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 13:05
     * @Return:
     */
    private Long hdel(Integer orderId) {
        return crudRedisManager.hdel(RedisKeyUtils.OrderKeys.ORDER_LOG, "查询订单日志的集合,Redis异常,Exception{},异常信息为:", orderId.toString());
    }

}
