package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCommentEntity;
import com.info.modules.product.service.IProductCommentService;
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
 * 商品评论信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("api/productComment")
public class ProductCommentController extends AbstractController {

    @Autowired
    private IProductCommentService productCommentService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productCommentService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @GetMapping("/info/{commId}")
    public ResultMessage info(@PathVariable("commId") Integer commId) {
        ProductCommentEntity productComment = productCommentService.getById(commId);
        return ResultMessage.ok(productComment);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody ProductCommentEntity productComment) {
        ValidatorUtils.validateEntity(productComment, AddGroup.class);
        productComment.setCreatorTime(DateUtils.now());
        productCommentService.save(productComment);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody ProductCommentEntity productComment) {
        ValidatorUtils.validateEntity(productComment, UpdateGroup.class);
        productComment.setEditorTime(DateUtils.now());
        productCommentService.updateById(productComment);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] commIds) {
        productCommentService.removeByIds(Arrays.asList(commIds));
        return ResultMessage.ok();
    }

}
