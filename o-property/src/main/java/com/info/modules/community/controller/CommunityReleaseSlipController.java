package com.info.modules.community.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.community.form.CommunityReleaseForm;
import com.info.modules.community.service.ICommunityReleaseSlipService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
@RestController
@RequestMapping("api/communityReleaseSlip")
public class CommunityReleaseSlipController extends AbstractController {


    @Autowired
    private ICommunityReleaseSlipService communityReleaseSlipService;


    /**
     * @Description 物业查询电子放行单的验证码是否可用
     * @Author LiuDan
     * @Date 2019/6/25 19:50
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getCheckCode")
    public ResultMessage list(String checkCode) {
        return communityReleaseSlipService.getCheckCode(checkCode);
    }


}
