package com.info.modules.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.pay.dao.IPayTypeDao;
import com.info.modules.pay.entity.PayTypeEntity;
import com.info.modules.pay.service.IPayTypeService;
import com.info.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 支付类型表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-09-08 18:52:52
 */
@Service("payTypeService")
public class PayTypeServiceImpl extends ServiceImpl<IPayTypeDao, PayTypeEntity> implements IPayTypeService {

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 支付类型表
     * @Date: 2018-09-08 18:52:52
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        return null;
    }

}
