package com.info.modules.move.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import com.info.modules.move.service.IMoveInfoCommentService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 社区活动留言表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@RestController
@RequestMapping("move/comment")
public class MoveInfoCommentController extends AbstractController {

    @Autowired
    private IMoveInfoCommentService moveInfoCommentService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/list")
    @RequiresPermissions("move:comment:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * @Author: Gaosx
     * @Description: 获取详细信息
     * @Date: 2019-06-06 18:05:24
     */
    @RequiresPermissions("move:comment:info")
    @RequestMapping("/info/{commentId}")
    public ResultMessage info(@PathVariable("commentId") Integer commentId) {
        MoveInfoCommentEntity moveInfoComment = moveInfoCommentService.getById(commentId);
        return ResultMessage.ok(moveInfoComment);
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:00
     * @Return:
     */
    @SysLog
    @RequestMapping("/del")
    @RequiresPermissions("move:comment:del")
    public ResultMessage del(@RequestBody Integer[] commentIds) {
        moveInfoCommentService.removeByIds(Arrays.asList(commentIds));
        return ResultMessage.ok();
    }

}
