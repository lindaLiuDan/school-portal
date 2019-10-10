package com.info.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.order.entity.OrderInfoDetailEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.order.dao.IOrderInfoDetailDao;
import com.info.modules.order.service.IOrderInfoDetailService;
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
 * 订单详情信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Service("orderInfoDetailService")
public class OrderInfoDetailServiceImpl extends ServiceImpl<IOrderInfoDetailDao, OrderInfoDetailEntity> implements IOrderInfoDetailService {


    @Autowired
    private ICrudRedisManager<OrderInfoDetailEntity> crudRedisManager;


    /**
     * 功能描述: 订单详情信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:27
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OrderInfoDetailEntity> page = this.page(
                new Query<OrderInfoDetailEntity>().getPage(params),
                new QueryWrapper<OrderInfoDetailEntity>()
                        .select("open_id,city_name,city_no,parent_id")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据订单号查询订单详情集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 17:19
     * @Return:
     */
    @Override
    public List<OrderInfoDetailEntity> getOrderIdItemList(Integer orderId) {
        String items = crudRedisManager.hget(RedisKeyUtils.OrderKeys.ORDER_ITEM, orderId.toString(), "获取ORDER_ITEM：" + orderId + "异常：");
        if (items == null) {
            List<OrderInfoDetailEntity> list = this.list(new QueryWrapper<OrderInfoDetailEntity>()
                    .select("item_id,order_id,pro_id,pro_price,numbers,buy_type")
                    .eq("order_id", orderId)
            );
            if (list != null) {
                crudRedisManager.hset(RedisKeyUtils.OrderKeys.ORDER_ITEM, orderId.toString(), JSON.toJSONString(list), "存储ORDER_ITEM：" + orderId + "异常：");
            }
            return list;
        }
        return JSON.parseArray(items, OrderInfoDetailEntity.class);
    }

}
