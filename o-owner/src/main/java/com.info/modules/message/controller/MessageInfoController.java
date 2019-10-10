package com.info.modules.message.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.message.entity.MessageInfoEntity;
import com.info.modules.message.service.IMessageInfoService;
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
 * 消息通知表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-18 11:40:10
 */
@RestController
@RequestMapping("api/messageInfo")
public class MessageInfoController extends AbstractController {

    @Autowired
    private IMessageInfoService messageInfoService;


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
        PageUtils page = messageInfoService.queryPage(params);
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
    @GetMapping("/info/{contentId}")
    public ResultMessage info(@PathVariable("contentId") Integer contentId) {
        MessageInfoEntity messageInfo = messageInfoService.getMessageByInfo(contentId);
        return ResultMessage.ok(messageInfo);
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
     * @Author:  Gaosx  
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody MessageInfoEntity messageInfo) {
        ValidatorUtils.validateEntity(messageInfo, UpdateGroup.class);
        messageInfo.setEditorTime(DateUtils.now());
        messageInfoService.updateById(messageInfo);//全部更新
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
    public ResultMessage delete(@RequestBody Integer[] infoIds) {
        messageInfoService.removeByIds(Arrays.asList(infoIds));
        return ResultMessage.ok();
    }

}
