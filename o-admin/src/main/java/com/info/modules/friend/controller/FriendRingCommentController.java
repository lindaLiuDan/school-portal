package com.info.modules.friend.controller;

import com.info.modules.friend.service.IFriendRingCommentService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
@RestController
@RequestMapping("friend/comment")
public class FriendRingCommentController extends AbstractController {

    @Autowired
    private IFriendRingCommentService friendRingCommentService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:18
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("friend:comment:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 查看评论信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:17
     * @Return:
     */
    @RequestMapping("/info/{commentId}")
    @RequiresPermissions("friend:comment:info")
    public ResultMessage save(@PathVariable("commentId") Integer commentId) {
        Assert.isNull(commentId, "评论ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(friendRingCommentService.getFriendRingComment(commentId));
    }

    /**
     * 功能描述: 删除评论信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:17
     * @Return:
     */
    @RequestMapping("/del/{commentId}")
    @RequiresPermissions("friend:comment:del")
    public ResultMessage del(@PathVariable("commentId") Integer commentId) {
        Assert.isNull(commentId, "评论ID不能为空", ConfigConstant.ERROR);
        return friendRingCommentService.delFriendRingComment(commentId);
    }

}
