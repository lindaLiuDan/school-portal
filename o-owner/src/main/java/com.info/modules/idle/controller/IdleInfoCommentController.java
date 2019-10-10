package com.info.modules.idle.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoCommentEntity;
import com.info.modules.idle.service.IdleInfoCommentService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 闲置交易评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("api/idleComment")
public class IdleInfoCommentController extends AbstractController {

    @Autowired
    private IdleInfoCommentService idleInfoCommentService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/22 19:57
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 删除
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/22 19:57
     * @Return:
     */
    @Login
    @PostMapping("/del/{commentId}/{idleId}")
    public ResultMessage info(@PathVariable("commentId") Integer commentId, @PathVariable("idleId") Integer idleId) {
        Assert.isNull(commentId,"评论ID不能为空",ConfigConstant.ERROR);
        Assert.isNull(idleId,"评论ID不能为空",ConfigConstant.ERROR);
        return idleInfoCommentService.delIdleComment(commentId, idleId);
    }

    /**
     * 功能描述: 保存
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/22 19:56
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody IdleInfoCommentEntity idleInfoComment) {
        ValidatorUtils.validateEntity(idleInfoComment, AddGroup.class);
        idleInfoComment.setCreatorTime(DateUtils.now());
        idleInfoCommentService.save(idleInfoComment);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/22 19:56
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody IdleInfoCommentEntity idleInfoComment) {
        ValidatorUtils.validateEntity(idleInfoComment, UpdateGroup.class);
        idleInfoComment.setEditorTime(DateUtils.now());
        idleInfoCommentService.updateById(idleInfoComment);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/22 19:56
     * @Return:
     */
    @Login
    @GetMapping("/info/{commentId}")
    public ResultMessage info(@PathVariable("commentId") Integer commentId) {
        IdleInfoCommentEntity idleInfoComment = idleInfoCommentService.getById(commentId);
        return ResultMessage.ok(idleInfoComment);
    }

}
