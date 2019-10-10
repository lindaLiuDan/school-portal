package com.info.modules.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.pay.dao.IPayLogDao;
import com.info.modules.pay.entity.PayLogEntity;
import com.info.modules.pay.service.IPayLogService;
import com.info.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 功能描述:
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/1/2 18:49
 */
@Service("payLogService")
public class PayLogServiceImpl extends ServiceImpl<IPayLogDao, PayLogEntity> implements IPayLogService {


    /**
     * 功能描述: 后台使用---支付日志信息表
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2019/3/11 11:23
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        return null;
    }

}
