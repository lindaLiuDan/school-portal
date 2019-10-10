package com.info.modules.friend.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.friend.form.FriendsRingForm;
import com.info.modules.friend.vo.FriendRingVo;
import com.info.modules.friend.service.IFriendRingService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
@RestController
@RequestMapping("/api/friendRing")
public class FriendRingController extends AbstractController {

    @Autowired
    private IFriendRingService friendRingService;


    /**
     * @Description 朋友圈列表_app
     * @Author LiuDan
     * @Date 2019/6/22 13:12
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getList")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * @Description 朋友圈详情_app
     * @Author LiuDan
     * @Date 2019/6/22 13:15
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/detail")
    public ResultMessage info(Integer ringId, Integer userId) {
        FriendRingVo friendRing = friendRingService.getDetail(ringId, userId);
        return ResultMessage.ok(friendRing);
    }


    /**
     * @Description 个人中心——查询我的发布（朋友圈）
     * @Author LiuDan
     * @Date 2019/6/17 14:44
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getMyFriendsRing")
    public ResultMessage getMyFriendsRing(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingService.queryPage(params);
        return ResultMessage.ok(page);
    }


    /**
     * @param
     * @return
     * @description: 上传图片__暂时不用测试
     * @author liudan
     * @date 2019/4/19 15:34
     */
    @Login
    @RequestMapping("/upload")
    public ResultMessage upload(MultipartFile[] file) {
        return friendRingService.upload(file);
    }

    /**
     * @Description 发布朋友圈————app
     * @Author LiuDan
     * @Date 2019/6/22 14:55
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/saveFriendsRing")
    public ResultMessage saveFriendsRing(FriendsRingForm form) {
        return friendRingService.saveFriendsRing(form);
    }


}
