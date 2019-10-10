package com.info.modules.community.controller;

import com.info.date.DateUtils;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
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
 * 社区小区信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-11 15:08:22
 */
@RestController
@RequestMapping("community/info")
public class CommunityInfoController extends AbstractController {

    @Autowired
    private ICommunityInfoService communityInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("community:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/info/{infoId}")
    @RequiresPermissions("community:info")
    public ResultMessage info(@PathVariable("infoId") Integer infoId) {
        CommunityInfoEntity communityInfo = communityInfoService.getById(infoId);
        return ResultMessage.ok(communityInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("community:save")
    public ResultMessage save(@RequestBody CommunityInfoEntity communityInfo) {
        ValidatorUtils.validateEntity(communityInfo,ConfigConstant.ERROR, AddGroup.class);
        communityInfo.setCreator(getUserId().intValue());
        communityInfo.setCreatorTime(DateUtils.now());
        communityInfoService.save(communityInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("community:update")
    public ResultMessage update(@RequestBody CommunityInfoEntity communityInfo) {
        ValidatorUtils.validateEntity(communityInfo, ConfigConstant.ERROR, UpdateGroup.class);
        communityInfo.setEditor(getUserId().intValue());
        communityInfo.setEditorTime(DateUtils.now());
        communityInfoService.updateById(communityInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("community:del")
    public ResultMessage delete(@RequestBody Integer[] infoIds) {
        communityInfoService.removeByIds(Arrays.asList(infoIds));
        return ResultMessage.ok();
    }

}
