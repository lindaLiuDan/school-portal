package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCateEntity;
import com.info.modules.product.service.IProductCateService;
import com.info.modules.product.vo.ProductCateVO;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@RestController
@RequestMapping("api/cate")
public class ProductCateController extends AbstractController {

    @Autowired
    private IProductCateService productCateService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productCateService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 根据父类ID递归查询下面所有的子类别--符合查询接口支持多种样式查询
     * cateId=0的时候查询所有的类别机器子类别信息集合
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 10:03
     * @Return:
     */
    @Login
    @GetMapping("/all/{cateId}")
    public ResultMessage all(@PathVariable("cateId") Integer cateId, @PathVariable("typeId") Integer typeId) {
        Assert.isNull(cateId,"分类ID不能为空", ConfigConstant.ERROR);
//        Assert.isNull(typeId,"查询类型不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(productCateService.getParentIdList(cateId,typeId));
    }

    /**
     * 功能描述: 获取商品类型详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/info/{cateId}")
    public ResultMessage info(@PathVariable("cateId") Integer cateId) {
        Assert.isNull(cateId,"商品类型ID不能为空", ConfigConstant.ERROR);
        ProductCateVO productCate = productCateService.getProductCateById(cateId);
        return ResultMessage.ok(productCate);
    }
}
