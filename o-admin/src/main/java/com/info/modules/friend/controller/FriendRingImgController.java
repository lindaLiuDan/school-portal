package com.info.modules.friend.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.friend.service.IFriendRingImgService;
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
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@RestController
@RequestMapping("friend/img")
public class FriendRingImgController extends AbstractController {

    @Autowired
    private IFriendRingImgService friendRingImgService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:24
     */
    @RequestMapping("/list")
    @RequiresPermissions("friend:img:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = friendRingImgService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 删除信息---暂时废弃的方法
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{imgId}")
    @RequiresPermissions("friend:img:del")
    public ResultMessage delete(@PathVariable("imgId") Integer imgId, @PathVariable("ringId") Integer ringId) {
        Assert.isNull(ringId,"朋友圈ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(imgId,"图片ID不能为空", ConfigConstant.ERROR);
        return friendRingImgService.delFriendRingImg(imgId, ringId);
    }


}
