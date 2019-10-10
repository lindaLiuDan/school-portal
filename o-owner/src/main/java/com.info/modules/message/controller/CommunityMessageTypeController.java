package com.info.modules.message.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.modules.message.service.ICommunityMessageTypeService;
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
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-17 17:52:42
 */
@RestController
@RequestMapping("api/noticeType")
public class CommunityMessageTypeController extends AbstractController {

    @Autowired
    private ICommunityMessageTypeService communityMessageTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityMessageTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @GetMapping("/info/{typeId}")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        CommunityMessageTypeEntity communityMessageType = communityMessageTypeService.getById(typeId);
        return ResultMessage.ok(communityMessageType);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody CommunityMessageTypeEntity communityMessageType) {
        ValidatorUtils.validateEntity(communityMessageType, AddGroup.class);
        communityMessageType.setCreatorTime(DateUtils.now());
        communityMessageTypeService.save(communityMessageType);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody CommunityMessageTypeEntity communityMessageType) {
        ValidatorUtils.validateEntity(communityMessageType, UpdateGroup.class);
        communityMessageType.setEditorTime(DateUtils.now());
        communityMessageTypeService.updateById(communityMessageType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] typeIds) {
        communityMessageTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
