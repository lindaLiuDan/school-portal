package com.info.modules.provider.controller;

import com.info.date.DateUtils;
import com.info.modules.provider.entity.ProviderGradeEntity;
import com.info.modules.provider.service.IProviderGradeService;
import com.info.modules.sys.controller.AbstractController;
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
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("provider/grade")
public class ProviderGradeController extends AbstractController {

    @Autowired
    private IProviderGradeService providerGradeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("providerGrade:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerGradeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @GetMapping("/info/{gradeId}")
    @RequiresPermissions("providerGrade:info")
    public ResultMessage info(@PathVariable("gradeId") Integer gradeId) {
        ProviderGradeEntity providerGrade = providerGradeService.getById(gradeId);
        return ResultMessage.ok(providerGrade);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("providerGrade:save")
    public ResultMessage save(@RequestBody ProviderGradeEntity providerGrade) {
        ValidatorUtils.validateEntity(providerGrade, AddGroup.class);
        providerGrade.setCreator(getUserId().intValue());
        providerGrade.setCreatorTime(DateUtils.now());
        providerGradeService.save(providerGrade);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("providerGrade:update")
    public ResultMessage update(@RequestBody ProviderGradeEntity providerGrade) {
        ValidatorUtils.validateEntity(providerGrade, UpdateGroup.class);
        providerGrade.setEditor(getUserId().intValue());
        providerGrade.setEditorTime(DateUtils.now());
        providerGradeService.updateById(providerGrade);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("providerGrade:del")
    public ResultMessage del(@RequestBody Integer[] gradeIds) {
        providerGradeService.removeByIds(Arrays.asList(gradeIds));
        return ResultMessage.ok();
    }

}
