package com.info.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.CodeEntity;
import com.info.utils.PageUtils;

import java.util.Date;
import java.util.Map;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 21:50:17
 */
public interface ICodeService extends IService<CodeEntity> {

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 验证码信息表
     * @Date: 2019-06-13 21:50:17
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description 保存验证码
     * @Author LiuDan
     * @Date 2019/6/13 22:04
     * @Param
     * @Return
     * @Exception
     */
    void saveInfo(Integer userId, String phone, Integer numbers, Date now, Date date, Integer type);


    /**
     * @Description 判断短信验证码是否正确
     * @Author LiuDan
     * @Date 2019/6/13 22:04
     * @Param
     * @Return
     * @Exception
     */
    boolean checkCode(String mobile, String code);

}

