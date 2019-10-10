package com.info.modules.community.controller;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.community.entity.CommunityPassInfoEntity;
import com.info.modules.community.form.CommunityPassInfoForm;
import com.info.modules.community.service.ICommunityPassInfoService;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.service.ISendService;
import com.info.number.RandomPickerUtils;
import com.info.string.Md5;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.utils.mobile.SendSMSALi;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Date;
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
     * @Description 查询我的同行记录分页查询
     * @Author LiuDan
     * @Date 2019/6/25 16:25
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityPassInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }



    /**
     * @Description 添加通行访问
     * @Author LiuDan
     * @Date 2019/6/25 14:47
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save1(CommunityPassInfoForm form) {
        ValidatorUtils.validateEntity(form, ConfigConstant.ERROR, AddGroup.class, UpdateGroup.class);
        //查询此电话号码的验证码是否已经超过6小时  如果没有超过6小时则不可以再次发送
        String mobile = form.getMobile();
        Boolean b = communityPassInfoService.getEntityByMobile(mobile, DateUtils.now());
        if (b) {
            return ResultMessage.ok("您发送过的验证码还没失效，不能再次发送");
        }
        return communityPassInfoService.savePassInfo(form);

    }

}
