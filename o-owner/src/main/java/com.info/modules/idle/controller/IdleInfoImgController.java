package com.info.modules.idle.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.modules.idle.service.IdleInfoImgService;
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
 * 闲置信息图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("api/idleImg")
public class IdleInfoImgController extends AbstractController {

    @Autowired
    private IdleInfoImgService idleInfoImgService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-07 17:17:37
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoImgService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * @Author: Gaosx
     * @Description: 获取详细信息
     * @Date: 2019-06-07 17:17:37
     */
    @Login
    @RequestMapping("/info/{imgId}")
    public ResultMessage info(@PathVariable("imgId") Integer imgId) {
        IdleInfoImgEntity idleInfoImg = idleInfoImgService.getById(imgId);
        return ResultMessage.ok(idleInfoImg);
    }

    /**
     * @Author: Gaosx
     * @Description: 保存信息
     * @Date: 2019-06-07 17:17:37
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody IdleInfoImgEntity idleInfoImg) {
        ValidatorUtils.validateEntity(idleInfoImg, AddGroup.class);
        idleInfoImg.setCreatorTime(DateUtils.now());
        idleInfoImgService.save(idleInfoImg);
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 修改信息
     * @Date: 2019-06-07 17:17:37
     */
    @Login
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody IdleInfoImgEntity idleInfoImg) {
        ValidatorUtils.validateEntity(idleInfoImg, UpdateGroup.class);
        idleInfoImg.setEditorTime(DateUtils.now());
        idleInfoImgService.updateById(idleInfoImg);//全部更新
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 删除信息
     * @Date: 2019-06-07 17:17:37
     */
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] imgIds) {
        idleInfoImgService.removeByIds(Arrays.asList(imgIds));
        return ResultMessage.ok();
    }

}
