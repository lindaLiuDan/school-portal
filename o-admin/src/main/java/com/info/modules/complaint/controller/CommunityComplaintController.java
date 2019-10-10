package com.info.modules.complaint.controller;

import com.info.date.DateUtils;
import com.info.modules.complaint.entity.CommunityComplaintEntity;
import com.info.modules.complaint.service.ICommunityComplaintService;
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
 * 社区投诉建议信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 14:31:58
 */
@RestController
@RequestMapping("complaint/info")
public class CommunityComplaintController extends AbstractController {

    @Autowired
    private ICommunityComplaintService communityComplaintService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("complaint:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityComplaintService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @GetMapping("/info/{complaintId}")
    @RequiresPermissions("complaint:info")
    public ResultMessage info(@PathVariable("complaintId") Integer complaintId) {
        Assert.isNull(complaintId,"投诉建议ID不能为空", ConfigConstant.ERROR);
        CommunityComplaintEntity communityComplaint = communityComplaintService.getById(complaintId);
        return ResultMessage.ok(communityComplaint);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("complaint:save")
    public ResultMessage save(@RequestBody CommunityComplaintEntity communityComplaint) {
        ValidatorUtils.validateEntity(communityComplaint,ConfigConstant.ERROR, AddGroup.class);
        communityComplaint.setCreator(getUserId().intValue());
        communityComplaint.setCreatorTime(DateUtils.now());
        communityComplaintService.save(communityComplaint);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("complaint:update")
    public ResultMessage update(@RequestBody CommunityComplaintEntity communityComplaint) {
        ValidatorUtils.validateEntity(communityComplaint,ConfigConstant.ERROR, UpdateGroup.class);
        communityComplaint.setEditor(getUserId().intValue());
        communityComplaint.setEditorTime(DateUtils.now());
        communityComplaintService.updateById(communityComplaint);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @PostMapping("/delete")
    @RequiresPermissions("complaint:delete")
    public ResultMessage delete(@RequestBody Integer[] complaintIds) {
        communityComplaintService.removeByIds(Arrays.asList(complaintIds));
        return ResultMessage.ok();
    }

}
