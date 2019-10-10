package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCommentTagEntity;
import com.info.modules.product.service.IProductCommentTagService;
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
 * 商品评论标签表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("api/commentTag")
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
    @Login
    @GetMapping("/list")
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
    @Login
    @GetMapping("/info/{tagId}")
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
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody ProductCommentTagEntity productCommentTag) {
        ValidatorUtils.validateEntity(productCommentTag, AddGroup.class);
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
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody ProductCommentTagEntity productCommentTag) {
        ValidatorUtils.validateEntity(productCommentTag, UpdateGroup.class);
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
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] tagIds) {
        productCommentTagService.removeByIds(Arrays.asList(tagIds));
        return ResultMessage.ok();
    }

}
