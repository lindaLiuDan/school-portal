package com.info.modules.sys.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.sys.dao.ISysCodeDao;
import com.info.modules.sys.entity.CodeEntity;
import com.info.modules.sys.service.ICodeService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 21:50:17
 */
@Service("sysCodeService")
public class CodeServiceImpl extends ServiceImpl<ISysCodeDao, CodeEntity> implements ICodeService {

    private static final Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class);

    @Autowired
    private ICrudRedisManager<CodeEntity> crudRedisManager;

    @Autowired
    private ISysCodeDao sysCodeDao;

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 验证码信息表
     * @Date: 2019-06-13 21:50:17
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        return null;
    }

    /**
     * @Description 保存验证码
     * @Author LiuDan
     * @Date 2019/6/13 22:04
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public void saveInfo(Integer userId, String phone, Integer numbers, Date now, Date date, Integer type) {
        CodeEntity codeEntity = new CodeEntity();
        //当前时间加5分钟
        codeEntity.setCreateTime(now);
        codeEntity.setExpireTime(date);
        codeEntity.setMobile(phone);
        codeEntity.setMobileCode(String.valueOf(numbers));
        if (userId != null) {
            codeEntity.setUserId(userId);
        }
        codeEntity.setIsType(type);
        sysCodeDao.insert(codeEntity);
        String key = RedisKeyUtils.UserInfoKyes.USER_INFO_CHECKCODE + phone;
        //只有访客通行码能在redis中放6个小时
        if (type.equals(ConfigConstant.SEND_TYPE_PASS)) {
            crudRedisManager.set(key, JSON.toJSONString(codeEntity), 60 * 60 * 60, "从redis中存入验证码信息异常，Redis异常,Exception{},异常信息为");
        }  String s = crudRedisManager.get(key, "获取redis中短信验证码是否正确，Redis异常,Exception{},异常信息为");
        crudRedisManager.set(key, numbers, 5 * 60, "从redis中存入验证码信息异常，Redis异常,Exception{},异常信息为");
    }

    /**
     * @Description 判断短信验证码是否正确
     * @Author LiuDan
     * @Date 2019/6/13 22:04
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public boolean checkCode(String mobile, String code) {
        String key = RedisKeyUtils.UserInfoKyes.USER_INFO_CHECKCODE + mobile;
        String s = crudRedisManager.get(key, "获取redis中短信验证码是否正确，Redis异常,Exception{},异常信息为");
        if (StringUtils.isNotBlank(s) && code.equalsIgnoreCase(s)) {
            return true;
        }
        CodeEntity codeEntity = sysCodeDao.findCode(mobile, code, DateUtils.now());
        if (codeEntity != null) {
            return true;
        }
        return false;
    }


}
