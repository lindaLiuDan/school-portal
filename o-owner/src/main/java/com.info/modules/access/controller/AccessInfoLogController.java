package com.info.modules.access.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.access.entity.AccessInfoLogEntity;
import com.info.modules.access.service.IAccessInfoLogService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 门禁开锁信息记录表
 *
 * @author Gaosx By user
 * @email 
 * @date 2019-06-14 12:11:40
 */
@RestController
@RequestMapping("api/access")
public class AccessInfoLogController extends AbstractController {

    @Autowired
    private IAccessInfoLogService accessInfoLogService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx By user  
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = accessInfoLogService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx By user  
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @Login
    @GetMapping("/info/{adId}")
    public ResultMessage info(@PathVariable("adId") Integer adId) {
        AccessInfoLogEntity accessInfoLog = accessInfoLogService.getById(adId);
        return ResultMessage.ok(accessInfoLog);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx By user  
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody AccessInfoLogEntity accessInfoLog) {
        ValidatorUtils.validateEntity(accessInfoLog, AddGroup.class);
        accessInfoLog.setCreatorTime(DateUtils.now());
        accessInfoLogService.save(accessInfoLog);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx By user  
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @Login
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody AccessInfoLogEntity accessInfoLog) {
        ValidatorUtils.validateEntity(accessInfoLog, UpdateGroup.class);
        accessInfoLog.setEditorTime(DateUtils.now());
        accessInfoLogService.updateById(accessInfoLog);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx By user  
     * @Date: 2019-06-14 12:11:40
     * @Return:
     */
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] adIds) {
        accessInfoLogService.removeByIds(Arrays.asList(adIds));
        return ResultMessage.ok();
    }

}
