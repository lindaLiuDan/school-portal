package com.info.modules.friend.controller;

import com.info.modules.friend.service.IFriendRingService;
import com.info.modules.friend.vo.FriendRingVo;
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
@RequestMapping("friend/ring")
public class FriendRingController extends AbstractController {

    @Autowired
    private IFriendRingService friendRingService;


    /**
     * 功能描述: 朋友圈列表分页查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 10:48
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("friend:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 朋友圈详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 10:49
     * @Return:
     */
    @RequestMapping("/info/{ringId}/{userId}}")
    @RequiresPermissions("friend:info")
    public ResultMessage info(@PathVariable("ringId") Integer ringId, @PathVariable("userId") Integer userId) {
        Assert.isNull(ringId, "朋友圈ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(userId, "用户ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(friendRingService.getDetail(ringId, userId));
    }

    /**
     * 功能描述: 删除朋友圈及其附属信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 10:49
     * @Return:
     */
    @RequestMapping("/del/{ringId}/{userId}}")
    @RequiresPermissions("friend:del")
    public ResultMessage del(@PathVariable("ringId") Integer ringId, @PathVariable("userId") Integer userId) {
        Assert.isNull(ringId, "朋友圈ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(userId, "用户ID不能为空", ConfigConstant.ERROR);
        return friendRingService.delFriendRing(ringId, userId);
    }

    /**
     * @param
     * @return
     * @description: 上传图片__暂时不用测试
     * @author liudan
     * @date 2019/4/19 15:34
     */
    @RequestMapping("/upload")
    @RequiresPermissions("friend:upload")
    public ResultMessage upload(MultipartFile[] file) {
        return friendRingService.upload(file);
    }


}
