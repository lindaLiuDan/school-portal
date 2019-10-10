package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCommentTagEntity;
import com.info.modules.product.service.IProductCommentTagService;
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
 * 商品评论标签表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("product/comment/tag")
public class ProductCommentTagController extends AbstractController {

    @Autowired
    private IProductCommentTagService productCommentTagService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("comment:tag:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productCommentTagService.queryPage(params);
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
    @GetMapping("/info/{tagId}")
    @RequiresPermissions("comment:tag:info")
    public ResultMessage info(@PathVariable("tagId") Integer tagId) {
        ProductCommentTagEntity productCommentTag = productCommentTagService.getById(tagId);
        return ResultMessage.ok(productCommentTag);
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
    @RequiresPermissions("comment:tag:save")
    public ResultMessage save(@RequestBody ProductCommentTagEntity productCommentTag) {
        ValidatorUtils.validateEntity(productCommentTag, AddGroup.class);
        productCommentTag.setCreator(getUserId().intValue());
        productCommentTag.setCreatorTime(DateUtils.now());
        productCommentTagService.save(productCommentTag);
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
    @RequiresPermissions("comment:tag:update")
    public ResultMessage update(@RequestBody ProductCommentTagEntity productCommentTag) {
        ValidatorUtils.validateEntity(productCommentTag, UpdateGroup.class);
        productCommentTag.setEditor(getUserId().intValue());
        productCommentTag.setEditorTime(DateUtils.now());
        productCommentTagService.updateById(productCommentTag);//全部更新
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
    @RequiresPermissions("comment:tag:del")
    public ResultMessage del(@RequestBody Integer[] tagIds) {
        productCommentTagService.removeByIds(Arrays.asList(tagIds));
        return ResultMessage.ok();
    }

}
