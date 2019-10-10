package com.info.modules.friend.controller;

import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.friend.entity.FriendRingCommentEntity;
import com.info.modules.friend.service.IFriendRingCommentService;
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
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
@RestController
@RequestMapping("/api/friendComment")
public class FriendRingCommentController extends AbstractController {

    @Autowired
    private IFriendRingCommentService friendRingCommentService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:25
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }



    /**
     * @Description 添加评论——app
     * @Author LiuDan
     * @Date 2019/6/12 15:49
     * @Param
     * @Return
     * @Exception
     */
    @RequestMapping("/save")
    public ResultMessage save(FriendRingCommentEntity friendRingComment) {
        ValidatorUtils.validateEntity(friendRingComment, AddGroup.class);
        friendRingComment.setCreatorTime(DateUtils.now());
        friendRingCommentService.save(friendRingComment);
        return ResultMessage.ok();
    }

}
