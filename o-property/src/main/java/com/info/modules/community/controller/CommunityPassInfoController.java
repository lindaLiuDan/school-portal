package com.info.modules.community.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.community.form.CommunityPassInfoForm;
import com.info.modules.community.service.ICommunityPassInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
@RestController
@RequestMapping("api/communityPassInfo")
public class CommunityPassInfoController extends AbstractController {


    @Autowired
    private ICommunityPassInfoService communityPassInfoService;


    /**
     * @Description 物业查询访客通行证的验证码是否可用
     * @Author LiuDan
     * @Date 2019/7/8 22:58
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/findCheckCode")
    public ResultMessage findCheckCode(String checkCode) {
        return communityPassInfoService.findCheckCode(checkCode);
    }


}
