package com.info.modules.message.controller;

import com.info.date.DateUtils;
import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.modules.message.service.ICommunityMessageTypeService;
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
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@RestController
@RequestMapping("community/message/type")
public class CommunityMessageTypeController extends AbstractController {

    @Autowired
    private ICommunityMessageTypeService communityMessageTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("community:messageType:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityMessageTypeService.queryPage(params);
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
    @GetMapping("/info/{typeId}")
    @RequiresPermissions("community:messageType:info")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        Assert.isNull(typeId, "类型ID不能为空", ConfigConstant.ERROR);
        CommunityMessageTypeEntity communityMessageType = communityMessageTypeService.getById(typeId);
        return ResultMessage.ok(communityMessageType);
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
    @RequiresPermissions("community:messageType:save")
    public ResultMessage save(@RequestBody CommunityMessageTypeEntity communityMessageType) {
        ValidatorUtils.validateEntity(communityMessageType, ConfigConstant.ERROR, AddGroup.class);
        communityMessageType.setCreator(getUserId().intValue());
        communityMessageType.setCreatorTime(DateUtils.now());
        Boolean flag = communityMessageTypeService.save(communityMessageType);
        if (flag) {
            ResultMessage.ok();
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
    @RequiresPermissions("community:messageType:update")
    public ResultMessage update(@RequestBody CommunityMessageTypeEntity communityMessageType) {
        ValidatorUtils.validateEntity(communityMessageType, ConfigConstant.ERROR, UpdateGroup.class);
        communityMessageType.setEditor(getUserId().intValue());
        communityMessageType.setEditorTime(DateUtils.now());
        return communityMessageTypeService.updateCommunityMessageType(communityMessageType);//全部更新
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
    @RequiresPermissions("community:messageType:del")
    public ResultMessage del(@RequestBody Integer typeId) {
        Assert.isNull(typeId, "类型ID不能为空", ConfigConstant.ERROR);
        return communityMessageTypeService.delCommunityMessageType(typeId);
    }

}
