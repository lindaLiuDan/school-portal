package com.info.modules.message.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.message.entity.MessageInfoTypeEntity;
import com.info.modules.message.service.IMessageInfoTypeService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 消息通知类型表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-18 11:40:10
 */
@RestController
@RequestMapping("api/messageType")
public class MessageInfoTypeController extends AbstractController {

    @Autowired
    private IMessageInfoTypeService messageInfoTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = messageInfoTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @GetMapping("/info/{typeId}")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        MessageInfoTypeEntity messageInfoType = messageInfoTypeService.getById(typeId);
        return ResultMessage.ok(messageInfoType);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody MessageInfoTypeEntity messageInfoType) {
        ValidatorUtils.validateEntity(messageInfoType, AddGroup.class);
        messageInfoType.setCreatorTime(DateUtils.now());
        messageInfoTypeService.save(messageInfoType);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody MessageInfoTypeEntity messageInfoType) {
        ValidatorUtils.validateEntity(messageInfoType, UpdateGroup.class);
        messageInfoType.setEditorTime(DateUtils.now());
        messageInfoTypeService.updateById(messageInfoType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] typeIds) {
        messageInfoTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
