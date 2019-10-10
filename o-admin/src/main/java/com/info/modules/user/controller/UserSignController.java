package com.info.modules.user.controller;

import com.info.date.DateUtils;
import com.info.modules.sys.controller.AbstractController;
import com.info.modules.user.entity.UserSignEntity;
import com.info.modules.user.service.IUserSignService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@RestController
@RequestMapping("user/sign")
public class UserSignController extends AbstractController {

    @Autowired
    private IUserSignService userSignService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("userSign:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = userSignService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @GetMapping("/info/{signId}")
    @RequiresPermissions("userSign:info")
    public ResultMessage info(@PathVariable("signId") Integer signId) {
        UserSignEntity userSign = userSignService.getById(signId);
        return ResultMessage.ok(userSign);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("userSign:save")
    public ResultMessage save(@RequestBody UserSignEntity userSign) {
        ValidatorUtils.validateEntity(userSign, AddGroup.class);
        userSign.setCreator(getUserId().intValue());
        userSign.setCreatorTime(DateUtils.now());
        userSignService.save(userSign);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("userSign:update")
    public ResultMessage update(@RequestBody UserSignEntity userSign) {
        ValidatorUtils.validateEntity(userSign, UpdateGroup.class);
        userSign.setEditor(getUserId().intValue());
        userSign.setEditorTime(DateUtils.now());
        userSignService.updateById(userSign);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("userSign:del")
    public ResultMessage del(@RequestBody Integer[] signIds) {
        userSignService.removeByIds(Arrays.asList(signIds));
        return ResultMessage.ok();
    }

}
