package com.info.modules.friend.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.friend.entity.FriendRingLikeEntity;
import com.info.modules.friend.service.IFriendRingLikeService;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@RestController
@RequestMapping("api/friendLike")
public class FriendRingLikeController extends AbstractController {

    @Autowired
    private IFriendRingLikeService friendRingLikeService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        Integer page = friendRingLikeService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }

    /**
     * @Description 朋友圈点赞
     * @Author LiuDan
     * @Date 2019/6/22 13:57
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(FriendRingLikeEntity friendRingLike) {
        ValidatorUtils.validateEntity(friendRingLike, AddGroup.class);
        friendRingLike.setCreatorTime(DateUtils.now());
        friendRingLikeService.saveRingLike(friendRingLike);
        return ResultMessage.ok();
    }


}
