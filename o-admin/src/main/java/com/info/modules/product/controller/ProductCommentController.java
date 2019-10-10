package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCommentEntity;
import com.info.modules.product.service.IProductCommentService;
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
 * 商品评论信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("product/comment")
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
    @GetMapping("/list")
    @RequiresPermissions("product:comment:list")
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
    @GetMapping("/info/{commId}")
    @RequiresPermissions("product:comment:info")
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
    @PostMapping("/save")
    @RequiresPermissions("product:comment:save")
    public ResultMessage save(@RequestBody ProductCommentEntity productComment) {
        ValidatorUtils.validateEntity(productComment, AddGroup.class);
        productComment.setCreator(getUserId().intValue());
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
    @PostMapping("/update")
    @RequiresPermissions("product:comment:update")
    public ResultMessage update(@RequestBody ProductCommentEntity productComment) {
        ValidatorUtils.validateEntity(productComment, UpdateGroup.class);
        productComment.setEditor(getUserId().intValue());
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
    @PostMapping("/del")
    @RequiresPermissions("product:comment:del")
    public ResultMessage del(@RequestBody Integer[] commIds) {
        productCommentService.removeByIds(Arrays.asList(commIds));
        return ResultMessage.ok();
    }

}
