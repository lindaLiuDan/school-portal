package com.info.modules.community.controller;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.modules.community.form.CommunityReleaseForm;
import com.info.modules.community.service.ICommunityReleaseSlipService;
import com.info.modules.sys.service.ICodeService;
import com.info.number.RandomPickerUtils;
import com.info.string.Md5;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.utils.mobile.SendSMSALi;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * todo 如果查询放行单发放记录  必须传递这个参数   slipResult
     *
     * @Description 电子放行单（查询申请记录/和放行单发放记录） __app
     * @Author LiuDan
     * @Date 2019/6/25 19:50
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityReleaseSlipService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * @Description 添加电子放行单————app
     * @Author LiuDan
     * @Date 2019/6/25 19:13
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@Valid @RequestBody CommunityReleaseForm form) {
        ValidatorUtils.validateEntity(form, ConfigConstant.ERROR, AddGroup.class, UpdateGroup.class);
        Boolean b = communityReleaseSlipService.getEntityByMobile(form.getMobile(), DateUtils.now());
        if (b) {
            return ResultMessage.error("您发送过的验证码还没失效，不能再次发送");
        }
        return communityReleaseSlipService.saveEntity(form);
    }

    /**
     * 功能描述: 查看放行单详情信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:10
     * @Return:
     */
    @Login
    @RequestMapping("/detail")
    public ResultMessage detail(Integer userId, Integer slipId) {
        return communityReleaseSlipService.getDetail(userId, slipId);
    }


    /**
     * @Description 申请记录
     * @Author LiuDan
     * @Date 2019/7/8 17:29
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getApplyList")
    public ResultMessage getApplyList(@RequestParam Map<String, Object> params) {
        PageUtils page = communityReleaseSlipService.getApplyList(params);
        return ResultMessage.ok(page);
    }


    /**
     * @Description 放行单发放记录
     * @Author LiuDan
     * @Date 2019/7/8 17:29
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getPassList")
    public ResultMessage getPassList(@RequestParam Map<String, Object> params) {
        PageUtils page = communityReleaseSlipService.getPassList(params);
        return ResultMessage.ok(page);
    }


}
