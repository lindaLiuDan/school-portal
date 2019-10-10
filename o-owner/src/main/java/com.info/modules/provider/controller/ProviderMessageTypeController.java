package com.info.modules.provider.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.provider.service.IProviderMessageTypeService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商家通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-14 19:42:01
 */
@RestController
@RequestMapping("api/providerMessageType")
public class ProviderMessageTypeController extends AbstractController {

    @Autowired
    private IProviderMessageTypeService providerMessageTypeService;


    /**
     * 功能描述: 列表所有类型
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    @Login
    @PostMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerMessageTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

}
