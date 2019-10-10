package com.info.modules.user.service.impl;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.dao.ISendDao;
import com.info.modules.user.form.SendSMSForm;
import com.info.modules.user.service.ISendService;
import com.info.number.RandomPickerUtils;
import com.info.string.Md5;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import com.info.utils.mobile.SendSMSALi;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 发送验证码公共方法
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Service("sendService")
public class SendServiceImpl extends ServiceImpl<ISendDao, SendSMSForm> implements ISendService {


    protected Logger logger = LoggerFactory.getLogger(SendServiceImpl.class);

    /**
     * 短信秘钥
     */
    @Value("${app_key}")
    public String appKey;
    /**
     * 阿里短信模板--注册
     */
    @Value("${templateCodeReg}")
    public String templateCodeReg;
    /**
     * 阿里短信模板--找回密码
     */
    @Value("${templateCodePwd}")
    public String templateCodePwd;
    /**
     * 阿里短信模板--修改绑定手机号
     */
    @Value("${templateCodeMobile}")
    public String templateCodeMobile;
    /**
     * 阿里短信模板--修改绑定手机号
     */
    @Value("${templateCodeWithDrawPwd}")
    public String templateCodeWithDrawPwd;


    @Autowired
    private ICodeService sysCodeService;

    @Override
    public ResultMessage send(SendSMSForm form) {
        ValidatorUtils.validateEntity(form, ConfigConstant.ERROR, AddGroup.class, UpdateGroup.class);
        Integer userId = form.getUserId();
        logger.info("加密秘钥：" + form.getMd());

        //appkey 给前台   前台传递过来一个时间戳和一个加密过后的密码    判断前台传来的密码是否是加密盐+时间
        String s = Md5.md5toUpperCase(appKey + form.getTimes());
        if (!s.equals(form.getMd())) {
            return ResultMessage.error(ConfigConstant.ERROR, "网络异常,请重试");
        }
        String phone = form.getMobile();
        Integer typeId = form.getTypeId();
        String templateCode = templateCodeReg;

        //状态报告       发送阿里短信
        Integer numbers = RandomPickerUtils.getRandomNum();
        SendSmsResponse response = SendSMSALi.sendMessage(phone, templateCode, numbers.toString());
        logger.info("短信接口返回的数据 Code = {},Message = {}, RequestId = {}, BizId = {}", response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());
        if ("isv.BUSINESS_LIMIT_CONTROL".equalsIgnoreCase(response.getCode())) {
            return ResultMessage.error(ConfigConstant.ERROR, "手机验证码已达上限,请联系客服");
        }
        //查明细
        if (response.getCode() != null && "OK".equals(response.getCode())) {
            QuerySendDetailsResponse querySendDetailsResponse = null;
            try {
                querySendDetailsResponse = SendSMSALi.querySendDetails(response.getBizId());
            } catch (ClientException e) {
                logger.error("短信查询信息异常", e);
            }
            logger.info("短信接口返回的数据 Code = {},Message = {}, RequestId = {}, BizId = {}", response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());
        }
        sysCodeService.saveInfo(userId, phone, numbers, form.getStartTime(), form.getEndTime(), typeId);
        return ResultMessage.ok(numbers);
    }
}
