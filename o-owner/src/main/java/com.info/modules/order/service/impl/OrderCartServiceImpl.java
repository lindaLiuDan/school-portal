package com.info.modules.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.order.entity.OrderCartEntity;
import com.info.modules.order.dao.IOrderCartDao;
import com.info.modules.order.service.IOrderCartService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 购物车信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:05
 */
@Service("orderCartService")
public class OrderCartServiceImpl extends ServiceImpl<IOrderCartDao, OrderCartEntity> implements IOrderCartService {

    /**
     * 功能描述: 购物车信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:30
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OrderCartEntity> page = this.page(
                new Query<OrderCartEntity>().getPage(params),
                new QueryWrapper<OrderCartEntity>()
                        .select("open_id,city_name,city_no,parent_id")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
