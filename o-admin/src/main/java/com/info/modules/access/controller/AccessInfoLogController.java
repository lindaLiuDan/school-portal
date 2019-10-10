package com.info.modules.access.controller;

import com.info.date.DateUtils;
import com.info.modules.access.entity.AccessInfoLogEntity;
import com.info.modules.access.service.IAccessInfoLogService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 门禁开锁信息记录表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 12:11:40
 */
@RestController
@RequestMapping("access/info")
public class AccessInfoLogController extends AbstractController {

    @Autowired
    private IAccessInfoLogService accessInfoLogService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("access:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = accessInfoLogService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @GetMapping("/info/{adId}")
    @RequiresPermissions("access:info")
    public ResultMessage info(@PathVariable("adId") Integer adId) {
        Assert.isNull(adId, "门禁开锁ID不能为空", ConfigConstant.ERROR);
        AccessInfoLogEntity accessInfoLog = accessInfoLogService.getById(adId);
        return ResultMessage.ok(accessInfoLog);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("access:save")
    public ResultMessage save(@RequestBody AccessInfoLogEntity accessInfoLog) {
        ValidatorUtils.validateEntity(accessInfoLog, ConfigConstant.ERROR, AddGroup.class);
        accessInfoLog.setCreator(getUserId().intValue());
        accessInfoLog.setCreatorTime(DateUtils.now());
        Boolean flag = accessInfoLogService.save(accessInfoLog);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("access:update")
    public ResultMessage update(@RequestBody AccessInfoLogEntity accessInfoLog) {
        ValidatorUtils.validateEntity(accessInfoLog, ConfigConstant.ERROR, UpdateGroup.class);
        accessInfoLog.setEditor(getUserId().intValue());
        accessInfoLog.setEditorTime(DateUtils.now());
        Boolean flag = accessInfoLogService.updateById(accessInfoLog);//全部更新
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("access:del")
    public ResultMessage del(@RequestBody Integer[] adIds) {
        Assert.isNull(adIds, "门禁开锁ID不能为空", ConfigConstant.ERROR);
        Boolean flag = accessInfoLogService.removeByIds(Arrays.asList(adIds));
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

}
