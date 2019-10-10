package com.info.modules.move.controller;

import com.info.modules.move.service.IMoveInfoSignupService;
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
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
@RestController
@RequestMapping("move/signup")
public class MoveInfoSignupController extends AbstractController {

    @Autowired
    private IMoveInfoSignupService moveInfoSignupService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:23
     */
    @RequestMapping("/list")
    @RequiresPermissions("move:signup:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoSignupService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 查看报名人信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:50
     * @Return:
     */
    @RequestMapping("/info/{upId}")
    @RequiresPermissions("move:signup:info")
    public ResultMessage info(@PathVariable("upId") Integer upId) {
        Assert.isNull(upId,"活动报名ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(moveInfoSignupService.getById(upId));
    }

    /**
     * 功能描述: 删除报名信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 14:50
     * @Return:
     */
    @RequestMapping("/del/{upId}")
    @RequiresPermissions("move:signup:del")
    public ResultMessage del(@PathVariable("upId") Integer upId) {
        Assert.isNull(upId,"活动报名ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(moveInfoSignupService.removeById(upId));
    }

}
