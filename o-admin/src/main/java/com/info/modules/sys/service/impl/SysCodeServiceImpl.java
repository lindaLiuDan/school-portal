package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.sys.dao.ISysCodeDao;
import com.info.modules.sys.entity.CodeEntity;
import com.info.modules.sys.service.ISysCodeService;
import com.info.redis.RedisKeyUtils;
import com.info.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-04-19 16:00:38
 */
@Service("sysCodeService")
public class SysCodeServiceImpl extends ServiceImpl<ISysCodeDao, CodeEntity> implements ISysCodeService {

    private static final Logger logger = LoggerFactory.getLogger(SysCodeServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @param phone 电话  numbers 验证码
     * @description: 保存短信验证码
     * @author Gaosx
     * @date 2019/4/19 17:59
     */
    @Override
    public void saveInfo(String phone, Integer numbers) {
        CodeEntity codeEntity = new CodeEntity();
        //当前时间加10分钟
        Date now = DateUtils.now();
        Date date = new Date(now.getTime() + 5 * 60 * 1000);
        codeEntity.setMobile(phone);
        codeEntity.setMobileCode(numbers.toString());
        codeEntity.setCreateTime(now);
        codeEntity.setExpireTime(date);
        baseMapper.insert(codeEntity);
        String key = RedisKeyUtils.UserInfoKyes.USER_INFO_CHECKCODE + phone;
        try {
            redisUtils.set(key, numbers, 5 * 60);
        } catch (Exception e) {
            logger.error("从redis中存入验证码信息异常，key:", key + phone + "异常信息为", e);
        }
    }

    @Override
    public boolean checkCode(String mobile, String code) {
        String key = RedisKeyUtils.UserInfoKyes.USER_INFO_CHECKCODE + mobile;
        try {
            String s = redisUtils.get(key);
            if (StringUtils.isNotBlank(s) && code.equalsIgnoreCase(s)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("从redis中存入验证码信息异常，key:", key + "异常信息为", e);
        }
        CodeEntity sysCodeEntity = baseMapper.findCode(mobile, code, DateUtils.now());
        if (sysCodeEntity != null) {
            return true;
        }
        return false;
    }
}
