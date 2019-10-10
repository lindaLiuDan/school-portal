package com.info.modules.message.controller;

import com.info.date.DateUtils;
import com.info.modules.message.entity.CommunityMessageEntity;
import com.info.modules.message.service.ICommunityMessageService;
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

import java.util.Map;

/**
 * 社区通告通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@RestController
@RequestMapping("community/message")
public class CommunityMessageController extends AbstractController {

    @Autowired
    private ICommunityMessageService communityMessageService;

    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("community:message:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityMessageService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @GetMapping("/info/{meId}")
    @RequiresPermissions("community:message:info")
    public ResultMessage info(@PathVariable("meId") Integer meId) {
        Assert.isNull(meId, "社区通告ID不能为空", ConfigConstant.ERROR);
        CommunityMessageEntity communityMessage = communityMessageService.getMessageById(meId);
        return ResultMessage.ok(communityMessage);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("community:message:save")
    public ResultMessage save(@RequestBody CommunityMessageEntity communityMessage) {
        ValidatorUtils.validateEntity(communityMessage, ConfigConstant.ERROR, AddGroup.class);
        communityMessage.setCreator(getUserId().intValue());
        communityMessage.setCreatorTime(DateUtils.now());
        Boolean flag = communityMessageService.save(communityMessage);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("community:message:update")
    public ResultMessage update(@RequestBody CommunityMessageEntity communityMessage) {
        ValidatorUtils.validateEntity(communityMessage, ConfigConstant.ERROR, UpdateGroup.class);
        communityMessage.setEditor(getUserId().intValue());
        communityMessage.setEditorTime(DateUtils.now());
        return communityMessageService.updateMessageInfo(communityMessage);//全部更新
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("community:message:del")
    public ResultMessage del(@PathVariable("meId") Integer meId) {
        Assert.isNull(meId, "社区通告ID不能为空", ConfigConstant.ERROR);
        return communityMessageService.delMessageInfo(meId);
    }

}
