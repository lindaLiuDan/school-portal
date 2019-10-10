package com.info.modules.message.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.message.entity.CommunityMessageEntity;
import com.info.modules.message.service.ICommunityMessageService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 社区通告通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@RestController
@RequestMapping("api/notice")
public class CommunityMessageController extends AbstractController {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 18:16
     * @Return:
     */
    @Autowired
    private ICommunityMessageService communityMessageService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        Assert.isNull(params.get("infoId"), "社区ID不能为空", 0);
        PageUtils page = communityMessageService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @GetMapping("/info/{meId}")
    public ResultMessage info(@PathVariable("meId") Integer meId) {
        CommunityMessageEntity communityMessage = communityMessageService.getMessageById(meId);
        return ResultMessage.ok(communityMessage);
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] meIds) {
        communityMessageService.removeByIds(Arrays.asList(meIds));
        return ResultMessage.ok();
    }

}
