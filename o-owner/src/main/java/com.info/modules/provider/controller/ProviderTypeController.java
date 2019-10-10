package com.info.modules.provider.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.provider.entity.ProviderTypeEntity;
import com.info.modules.provider.service.IProviderTypeService;
import com.info.modules.provider.vo.ProviderTypeEntityVo;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("api/providerType")
public class ProviderTypeController extends AbstractController {

    @Autowired
    private IProviderTypeService providerTypeService;


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
        PageUtils page = providerTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页获取商家分类信息表--如果parent_id不为空的分类，否则是所有的分类查询
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/24 16:37
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        Assert.isNull(params.get("parentId"), "ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(providerTypeService.getProviderTypeList(params));
    }

    /**
     * 功能描述: 查询所有分类信息一次性返回的是顶级父类和自己的子类
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 10:20
     * @Return:
     */
    @Login
    @GetMapping("/getTypeList")
    public ResultMessage getTypeList(@RequestParam Map<String, Object> params) {
        return ResultMessage.ok(providerTypeService.getTypeList(params));
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
    @GetMapping("/info/{typeId}")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        Assert.isNull(typeId, "分类ID不能为空", ConfigConstant.ERROR);
        ProviderTypeEntityVo providerType = providerTypeService.getProviderTypeById(typeId);
        return ResultMessage.ok(providerType);
    }


}
