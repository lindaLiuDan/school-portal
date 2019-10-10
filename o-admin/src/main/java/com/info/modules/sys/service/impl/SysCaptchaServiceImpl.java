package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.info.date.DateUtils;
import com.info.exception.LiftException;
import com.info.modules.sys.dao.ISysCaptchaDao;
import com.info.modules.sys.entity.SysCaptchaEntity;
import com.info.modules.sys.service.ISysCaptchaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * 功能描述: 验证码
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:48
 * @Return:
 */
@Service("sysCaptchaService")
public class SysCaptchaServiceImpl extends ServiceImpl<ISysCaptchaDao, SysCaptchaEntity> implements ISysCaptchaService {

    @Autowired
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isBlank(uuid)) {
            throw new LiftException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();

        SysCaptchaEntity captchaEntity = new SysCaptchaEntity();
        captchaEntity.setUuid(uuid);
        captchaEntity.setCode(code);
        //5分钟后过期
        captchaEntity.setExpireTime(DateUtils.addDateMinutes(new Date(), 5));
        this.save(captchaEntity);

        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptchaEntity captchaEntity = this.getOne(new QueryWrapper<SysCaptchaEntity>().eq("uuid", uuid));
//        SysCaptchaEntity captchaEntity = this.selectById(uuid);
        if (captchaEntity == null) {
            return false;
        }
        //删除验证码
//        this.deleteById(uuid);

        if (captchaEntity.getCode().equalsIgnoreCase(code) && captchaEntity.getExpireTime().getTime() >= System.currentTimeMillis()) {
            return true;
        }

        return false;
    }
}
