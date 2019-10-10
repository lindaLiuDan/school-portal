package com.info.modules.friend.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.modules.friend.service.IFriendRingLikeService;
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
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@RestController
@RequestMapping("friend/like")
public class FriendRingLikeController extends AbstractController {

    @Autowired
    private IFriendRingLikeService friendRingLikeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 11:04
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("friend:like:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingLikeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/info/{likeId}")
    @RequiresPermissions("friend:like:info")
    public ResultMessage info(@PathVariable("likeId") Integer likeId) {
        Assert.isNull(likeId,"点赞ID不能为空", ConfigConstant.ERROR);
        FriendRingLikeEntity friendRingLikeEntity = friendRingLikeService.getFriendRingLikeInfo(likeId);
        return ResultMessage.ok(friendRingLikeEntity);
    }

    /**
     * 功能描述: 删除信息---------------------------------------暂时废弃的方法
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{likeId}")
    @RequiresPermissions("friend:like:del")
    public ResultMessage delete(@PathVariable("likeId") Integer likeId) {
        Assert.isNull(likeId,"点赞ID不能为空", ConfigConstant.ERROR);
        friendRingLikeService.delFriendRingLike(likeId);
        return ResultMessage.ok();
    }

}
