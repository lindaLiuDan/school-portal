package com.info.modules.message.controller;

import com.info.date.DateUtils;
import com.info.modules.message.entity.MessageInfoEntity;
import com.info.modules.message.service.IMessageInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 消息通知表--推送消息
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 11:40:10
 */
@RestController
@RequestMapping("message/info")
public class MessageInfoController extends AbstractController {

    @Autowired
    private IMessageInfoService messageInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("messageInfo:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = messageInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @GetMapping("/info/{infoId}")
    @RequiresPermissions("messageInfo:info")
    public ResultMessage info(@PathVariable("infoId") Integer infoId) {
        Assert.isNull(infoId, "消息ID不能为空", ConfigConstant.ERROR);
        MessageInfoEntity messageInfo = messageInfoService.getById(infoId);
        return ResultMessage.ok(messageInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("messageInfo:save")
    public ResultMessage save(@RequestBody MessageInfoEntity messageInfo) {
        ValidatorUtils.validateEntity(messageInfo, AddGroup.class);
        messageInfo.setCreatorTime(DateUtils.now());
        messageInfoService.save(messageInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @PostMapping("/update")
    public ResultMessage update(@RequestBody MessageInfoEntity messageInfo) {
        ValidatorUtils.validateEntity(messageInfo, UpdateGroup.class);
        messageInfo.setEditor(getUserId().intValue());
        messageInfo.setEditorTime(DateUtils.now());
        messageInfoService.updateById(messageInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("messageInfo:del")
    public ResultMessage del(@RequestBody Integer[] infoIds) {
        Assert.isNull(infoIds, "消息ID不能为空", ConfigConstant.ERROR);
        messageInfoService.removeByIds(Arrays.asList(infoIds));
        return ResultMessage.ok();
    }

}
