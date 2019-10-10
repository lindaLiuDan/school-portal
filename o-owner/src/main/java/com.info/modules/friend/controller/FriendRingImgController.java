package com.info.modules.friend.controller;

import com.info.common.base.AbstractController;
import com.info.modules.friend.service.IFriendRingImgService;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@RestController
@RequestMapping("/api/friendImg")
public class FriendRingImgController extends AbstractController {

    @Autowired
    private IFriendRingImgService friendRingImgService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        Integer page = friendRingImgService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }

}
