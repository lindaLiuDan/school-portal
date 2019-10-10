package com.info.modules.complaint.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.complaint.entity.CommunityComplaintEntity;
import com.info.modules.complaint.service.ICommunityComplaintService;
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
 * 社区投诉建议信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 14:31:58
 */
@RestController
@RequestMapping("api/complaint")
public class CommunityComplaintController extends AbstractController {

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 14:37
     * @Return:
     */
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
    @Login
    @GetMapping("/list")
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
    @Login
    @GetMapping("/info/{complaintId}")
    public ResultMessage info(@PathVariable("complaintId") Integer complaintId) {
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
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody CommunityComplaintEntity communityComplaint) {
        ValidatorUtils.validateEntity(communityComplaint,0, AddGroup.class);
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
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody CommunityComplaintEntity communityComplaint) {
        ValidatorUtils.validateEntity(communityComplaint, UpdateGroup.class);
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
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] complaintIds) {
        communityComplaintService.removeByIds(Arrays.asList(complaintIds));
        return ResultMessage.ok();
    }

}
