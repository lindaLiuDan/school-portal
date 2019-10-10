package com.info.modules.area.address;

import com.info.date.DateUtils;
import com.info.modules.area.entity.OpenCityEntity;
import com.info.modules.area.service.IOpenCityService;
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
import java.util.List;
import java.util.Map;

/**
 * 开放城市区域信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-12 19:31:54
 */
@RestController
@RequestMapping("open/city")
public class OpenCityController extends AbstractController {

    @Autowired
    private IOpenCityService openCityService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("open:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = openCityService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页的查询所有开放城市
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:44
     * @Return:
     */
    @GetMapping("/all")
    @RequiresPermissions("open:list")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<OpenCityEntity> list = openCityService.all(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @GetMapping("/info/{openId}")
    @RequiresPermissions("open:info")
    public ResultMessage info(@PathVariable("openId") Integer openId) {
        Assert.isNull(openId,"开放城市ID不能为空", ConfigConstant.ERROR);
        OpenCityEntity openCity = openCityService.getById(openId);
        return ResultMessage.ok(openCity);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("open:save")
    public ResultMessage save(@RequestBody OpenCityEntity openCity) {
        ValidatorUtils.validateEntity(openCity, AddGroup.class);
        openCity.setCreatorTime(DateUtils.now());
        openCityService.save(openCity);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("open:update")
    public ResultMessage update(@RequestBody OpenCityEntity openCity) {
        ValidatorUtils.validateEntity(openCity, UpdateGroup.class);
        openCity.setEditorTime(DateUtils.now());
        openCityService.updateById(openCity);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("open:del")
    public ResultMessage del(@RequestBody Integer[] openIds) {
        openCityService.removeByIds(Arrays.asList(openIds));
        return ResultMessage.ok();
    }

}
