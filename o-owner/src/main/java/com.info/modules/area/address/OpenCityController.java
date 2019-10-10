package com.info.modules.area.address;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.area.entity.OpenCityEntity;
import com.info.modules.area.service.IOpenCityService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
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
@RequestMapping("api/open")
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
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = openCityService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页的查询所有开放城市
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/12 19:44
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<OpenCityEntity> page = openCityService.all(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-12 19:31:54
     * @Return:
     */
    @Login
    @GetMapping("/info/{openId}")
    public ResultMessage info(@PathVariable("openId") Integer openId) {
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
    @Login
    @RequestMapping("/save")
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
    @Login
    @RequestMapping("/update")
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
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] openIds) {
        openCityService.removeByIds(Arrays.asList(openIds));
        return ResultMessage.ok();
    }

}
