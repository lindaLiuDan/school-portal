package com.info.modules.idle.controller;

import com.info.common.annotation.SysLog;
import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoCommentEntity;
import com.info.modules.idle.service.IdleInfoCommentService;
import com.info.modules.sys.controller.AbstractController;
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
 * 闲置交易评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("idle/comment")
public class IdleInfoCommentController extends AbstractController {


    @Autowired
    private IdleInfoCommentService idleInfoCommentService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-07 17:17:37
     */
    @SysLog
    @RequestMapping("/list")
    @RequiresPermissions("idle:comment:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * @Author: Gaosx
     * @Description: 获取详细信息
     * @Date: 2019-06-07 17:17:37
     */
    @SysLog
    @RequestMapping("/info/{commentId}")
    @RequiresPermissions("idle:comment:info")
    public ResultMessage info(@PathVariable("commentId") Integer commentId) {
        IdleInfoCommentEntity idleInfoComment = idleInfoCommentService.getById(commentId);
        return ResultMessage.ok(idleInfoComment);
    }

    /**
     * @Author: Gaosx
     * @Description: 保存信息
     * @Date: 2019-06-07 17:17:37
     */
    @RequestMapping("/save")
    @RequiresPermissions("idle:comment:save")
    public ResultMessage save(@RequestBody IdleInfoCommentEntity idleInfoComment) {
        ValidatorUtils.validateEntity(idleInfoComment, AddGroup.class);
        idleInfoComment.setCreatorTime(DateUtils.now());
        idleInfoCommentService.save(idleInfoComment);
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 修改信息
     * @Date: 2019-06-07 17:17:37
     */
    @SysLog
    @RequestMapping("/update")
    @RequiresPermissions("idle:comment:update")
    public ResultMessage update(@RequestBody IdleInfoCommentEntity idleInfoComment) {
        ValidatorUtils.validateEntity(idleInfoComment, UpdateGroup.class);
        idleInfoComment.setEditorTime(DateUtils.now());
        idleInfoCommentService.updateById(idleInfoComment);//全部更新
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 删除信息
     * @Date: 2019-06-07 17:17:37
     */
    @SysLog
    @RequestMapping("/del")
    @RequiresPermissions("idle:comment:del")
    public ResultMessage del(@RequestBody Integer[] commentIds) {
        idleInfoCommentService.removeByIds(Arrays.asList(commentIds));
        return ResultMessage.ok();
    }

}
