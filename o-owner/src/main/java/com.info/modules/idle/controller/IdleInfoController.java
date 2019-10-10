package com.info.modules.idle.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoEntity;
import com.info.modules.idle.service.IdleInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("api/idleInfo")
public class IdleInfoController extends AbstractController {

    @Autowired
    private IdleInfoService idleInfoService;


    /**
     * 功能描述: 分页展示所有用户发布的闲置交易信息
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 7:56
     * @Return:
     */
    @Login
    @GetMapping("/page")
    public ResultMessage page(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 根据用户ID主键查询用户发布的闲置交易信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/22 15:30
     * @Return:
     */
    @Login
    @GetMapping("/idleList")
    public ResultMessage idleList(@RequestParam Map<String, Object> params) {
        Assert.isNull(params.get("userId"), "用户ID不能为空", ConfigConstant.ERROR);
        PageUtils page = idleInfoService.idleList(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 8:10
     * @Return:
     */
    @Login
    @GetMapping("/info/{leId}")
    public ResultMessage info(@PathVariable("leId") Integer leId) {
        Assert.isNull(leId, "详情ID不能为空", ConfigConstant.ERROR);
        IdleInfoEntity idleInfo = idleInfoService.loadById(leId);
        return ResultMessage.ok(idleInfo);
    }

    /**
     * 功能描述: 闲置交易-业主发布闲置交易信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/10 21:51
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody @Valid IdleInfoEntity idleInfoEntity) {
        ValidatorUtils.validateEntity(idleInfoEntity, ConfigConstant.ERROR, AddGroup.class);
        idleInfoEntity.setCreatorTime(DateUtils.now());
        return idleInfoService.saveIdleInfo(idleInfoEntity);
    }

    /**
     * 功能描述: 闲置交易-用户删除发布交易信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/10 21:51
     * @Return:
     */
    @Login
    @PostMapping("/del/{leId}")
    public ResultMessage del(@PathVariable("leId") Integer leId) {
        Assert.isNull(leId, "交易信息ID不能为空", ConfigConstant.ERROR);
        return idleInfoService.delIdleInfo(leId);
    }


}
