package com.info.modules.idle.controller;

import com.info.common.annotation.SysLog;
import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoEntity;
import com.info.modules.idle.service.IdleInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("idle/info")
public class IdleInfoController extends AbstractController {

    @Autowired
    private IdleInfoService idleInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 7:56
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("idle:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 8:10
     * @Return:
     */
    @GetMapping("/info/{leId}")
    @RequiresPermissions("idle:info")
    public ResultMessage info(@PathVariable("leId") Integer leId) {
        IdleInfoEntity idleInfo = idleInfoService.getIdleInfoById(leId);
        return ResultMessage.ok(idleInfo);
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 8:11
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{leId}")
    @RequiresPermissions("idle:del")
    public ResultMessage delete(@PathVariable("leId") Integer leId) {
        return idleInfoService.delIdleInfo(leId);
    }

}
