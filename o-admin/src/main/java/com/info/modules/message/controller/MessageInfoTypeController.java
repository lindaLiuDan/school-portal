package com.info.modules.message.controller;

import com.info.date.DateUtils;
import com.info.modules.message.entity.MessageInfoTypeEntity;
import com.info.modules.message.service.IMessageInfoTypeService;
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
 * 功能描述: 消息通知类型表
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/16 10:27
 * @Return:
 */
@RestController
@RequestMapping("message/type")
public class MessageInfoTypeController extends AbstractController {

    @Autowired
    private IMessageInfoTypeService messageInfoTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("messageType:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = messageInfoTypeService.queryPage(params);
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
    @GetMapping("/info/{typeId}")
    @RequiresPermissions("messageType:info")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        Assert.isNull(typeId, "类型ID不能为空", ConfigConstant.ERROR);
        MessageInfoTypeEntity messageInfoType = messageInfoTypeService.getById(typeId);
        return ResultMessage.ok(messageInfoType);
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
    @RequiresPermissions("messageType:save")
    public ResultMessage save(@RequestBody MessageInfoTypeEntity messageInfoType) {
        ValidatorUtils.validateEntity(messageInfoType, ConfigConstant.ERROR, AddGroup.class);
        messageInfoType.setCreator(getUserId().intValue());
        messageInfoType.setCreatorTime(DateUtils.now());
        messageInfoTypeService.save(messageInfoType);
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
    @RequiresPermissions("messageType:update")
    public ResultMessage update(@RequestBody MessageInfoTypeEntity messageInfoType) {
        ValidatorUtils.validateEntity(messageInfoType,ConfigConstant.ERROR, UpdateGroup.class);
        messageInfoType.setEditor(getUserId().intValue());
        messageInfoType.setEditorTime(DateUtils.now());
        messageInfoTypeService.updateById(messageInfoType);//全部更新
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
    @RequiresPermissions("messageType:del")
    public ResultMessage del(@RequestBody Integer[] typeIds) {
        Assert.isNull(typeIds, "类型ID不能为空", ConfigConstant.ERROR);
        messageInfoTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
