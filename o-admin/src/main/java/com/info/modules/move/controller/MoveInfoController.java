package com.info.modules.move.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.move.entity.MoveInfoEntity;
import com.info.modules.move.service.IMoveInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
@RestController
@RequestMapping("move/info")
public class MoveInfoController extends AbstractController {

    @Autowired
    private IMoveInfoService moveInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:05
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("move:info:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 添加活动列表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 14:45
     * @Return:
     */
    @SysLog
    @RequestMapping("/save")
    @RequiresPermissions("move:info:save")
    public ResultMessage getList(@RequestBody MoveInfoEntity moveInfoEntity) {
        ValidatorUtils.validateEntity(moveInfoEntity, ConfigConstant.ERROR, AddGroup.class);
        Boolean flag = moveInfoService.save(moveInfoEntity);
        if (flag) {
            return ResultMessage.ok("添加活动信息成功");
        }
        return ResultMessage.error(ConfigConstant.ERROR, "添加失败");
    }

    /**
     * 功能描述: 查询活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:06
     * @Return:
     */
    @SysLog
    @RequestMapping("/info/{moveId}")
    @RequiresPermissions("move:info:info")
    public ResultMessage getDetail(@PathVariable("moveId") Integer moveId) {
        Assert.isNull(moveId, "活动ID不能为空", ConfigConstant.ERROR);
        MoveInfoEntity moveInfoEntity = moveInfoService.getMoveInfo(moveId);
        return ResultMessage.ok(moveInfoEntity);
    }

    /**
     * 功能描述: 修改活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:06
     * @Return:
     */
    @SysLog
    @RequestMapping("/update/{moveId}")
    @RequiresPermissions("move:info:update")
    public ResultMessage update(@RequestBody MoveInfoEntity moveInfoEntity) {
        ValidatorUtils.validateEntity(moveInfoEntity, ConfigConstant.ERROR, AddGroup.class);
        return moveInfoService.updateMoveInfo(moveInfoEntity);
    }

    /**
     * 功能描述: 查询活动详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:06
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{moveId}")
    @RequiresPermissions("move:info:del")
    public ResultMessage del(@PathVariable("moveId") Integer moveId) {
        Assert.isNull(moveId, "活动ID不能为空", ConfigConstant.ERROR);
        return moveInfoService.delMoveInfo(moveId);
    }

}
