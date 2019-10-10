package com.info.modules.move.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.move.entity.MoveInfoCommentEntity;
import com.info.modules.move.service.IMoveInfoCommentService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("api/moveInfoComment")
public class MoveInfoCommentController extends AbstractController {

    @Autowired
    private IMoveInfoCommentService moveInfoCommentService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoCommentService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }

    /**
     * @Author: Gaosx
     * @Description: 获取详细信息
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/info/{commentId}")
    public ResultMessage info(@PathVariable("commentId") Integer commentId) {
        MoveInfoCommentEntity moveInfoComment = moveInfoCommentService.getById(commentId);
        return ResultMessage.ok().put("moveInfoComment", "");
    }

    /**
     * @Author: Gaosx
     * @Description: 保存信息
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody MoveInfoCommentEntity moveInfoComment) {
        ValidatorUtils.validateEntity(moveInfoComment, AddGroup.class);
        moveInfoComment.setCreatorTime(DateUtils.now());
        moveInfoCommentService.save(moveInfoComment);
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 修改信息
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody MoveInfoCommentEntity moveInfoComment) {
        ValidatorUtils.validateEntity(moveInfoComment, UpdateGroup.class);
        moveInfoComment.setEditorTime(DateUtils.now());
        moveInfoCommentService.updateById(moveInfoComment);//全部更新
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 删除信息
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] commentIds) {
        moveInfoCommentService.removeByIds(Arrays.asList(commentIds));
        return ResultMessage.ok();
    }


    /**
     * @Description 活动留言列表
     * @Author LiuDan
     * @Date 2019/6/10 9:30
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getList")
    public ResultMessage getList(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }


    /**
     * @Description 添加社区活动留言
     * @Author LiuDan
     * @Date 2019/6/10 11:19
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/addMoveInfoComment")
    public ResultMessage addMoveInfoComment(Integer userId, Integer moveId, String comment) {
        if (!StringUtils.isNotBlank(comment) || userId == null || moveId == null) {
            return ResultMessage.error("参数错误");
        }
        MoveInfoCommentEntity entity = new MoveInfoCommentEntity();
        entity.setCreatorTime(DateUtils.now());
        entity.setUserId(userId);
        entity.setMoveId(moveId);
        entity.setComment(comment);
        boolean save = moveInfoCommentService.save(entity);
        if (save) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.error();
        }
    }

}
