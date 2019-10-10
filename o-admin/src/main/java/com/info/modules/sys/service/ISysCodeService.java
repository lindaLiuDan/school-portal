package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.CodeEntity;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-04-19 16:00:38
 */
public interface ISysCodeService extends IService<CodeEntity> {

    /**
     * @param phone 电话  numbers 验证码
     * @description: 保存短信验证码
     * @author Gaosx
     * @date 2019/4/19 17:59
     */
    void saveInfo(String phone, Integer numbers);

    /**
     * @param mobile
     * @param code
     * @description: 判断短信验证码是否正确
     * @author Gaosx
     * @date 2019/4/19 17:59
     */
    boolean checkCode(String mobile, String code);
}

