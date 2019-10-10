package com.info.modules.provider.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.provider.service.IProviderGradeService;
import com.info.modules.provider.vo.ProviderGradeVO;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("api/providerGrade")
public class ProviderGradeController extends AbstractController {

    @Autowired
    private IProviderGradeService providerGradeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerGradeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页查询所有的等级信息集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 18:05
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        return ResultMessage.ok(providerGradeService.getProviderGradeList(params));
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @GetMapping("/info/{gradeId}")
    public ResultMessage info(@PathVariable("gradeId") Integer gradeId) {
        Assert.isNull(gradeId,"等级ID不能为空", ConfigConstant.ERROR);
        ProviderGradeVO providerGrade = providerGradeService.getProviderGradeById(gradeId);
        return ResultMessage.ok(providerGrade);
    }


}
