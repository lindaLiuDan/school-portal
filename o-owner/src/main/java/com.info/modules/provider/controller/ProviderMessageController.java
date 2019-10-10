package com.info.modules.provider.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.provider.service.IProviderMessageService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商户通知表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-14 19:42:01
 */
@RestController
@RequestMapping("api/providerMessage")
public class ProviderMessageController extends AbstractController {

    @Autowired
    private IProviderMessageService providerMessageService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    @Login
    @PostMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerMessageService.queryPage(params);
        return ResultMessage.ok(page);
    }



}
