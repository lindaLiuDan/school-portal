package com.info.modules.sys.controller;

import com.info.modules.sys.entity.SysMessageInfoEntity;
import com.info.modules.sys.service.ISysMessageInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 系统消息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-16 15:01:31
 */
@RestController
@RequestMapping("sys/messageInfo")
public class SysMessageInfoController extends AbstractController {

    @Autowired
    private ISysMessageInfoService sysMessageInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-16 15:01:31
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:messageInfo:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysMessageInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-16 15:01:31
     * @Return:
     */
    @GetMapping("/info/{messageId}")
    @RequiresPermissions("sys:messageInfo:info")
    public ResultMessage info(@PathVariable("messageId") Integer messageId) {
        Assert.isNull(messageId, "消息ID不能为空", ConfigConstant.ERROR);
        SysMessageInfoEntity sysMessageInfo = sysMessageInfoService.getMessageInfoById(messageId);
        return ResultMessage.ok(sysMessageInfo);
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-16 15:01:31
     * @Return:
     */
    @PostMapping("/delete")
    @RequiresPermissions("sys:messageInfo:delete")
    public ResultMessage delete(@RequestBody Integer[] messageIds) {
        Assert.isNull(messageIds, "消息ID不能为空", ConfigConstant.ERROR);
        sysMessageInfoService.removeByIds(Arrays.asList(messageIds));
        return ResultMessage.ok();
    }

}
