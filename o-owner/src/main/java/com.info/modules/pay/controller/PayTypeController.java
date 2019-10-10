package com.info.modules.pay.controller;

import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.pay.entity.PayTypeEntity;
import com.info.modules.pay.service.IPayTypeService;
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
 * 支付类型表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-09-08 18:52:52
 */
@RestController
@RequestMapping("api/payType")
public class PayTypeController extends AbstractController {

    @Autowired
    private IPayTypeService payTypeService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 列表查询
     * @Date: 2018-09-08 18:52:52
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = payTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 获取详细信息
     * @Date: 2018-09-08 18:52:52
     */
    @RequestMapping("/info/{typeId}")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        PayTypeEntity payType = payTypeService.getById(typeId);
        return ResultMessage.ok(payType);
    }

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 保存信息
     * @Date: 2018-09-08 18:52:52
     */
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody PayTypeEntity payType) {
        ValidatorUtils.validateEntity(payType, AddGroup.class);
//        payType.setCreator(getUserId().intValue());
        payType.setCreatorTime(DateUtils.now());
        payTypeService.save(payType);
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 修改信息
     * @Date: 2018-09-08 18:52:52
     */
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody PayTypeEntity payType) {
        ValidatorUtils.validateEntity(payType, UpdateGroup.class);
//        payType.setEditor(getUserId().intValue());
        payType.setEditorTime(DateUtils.now());
        payTypeService.updateById(payType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 删除信息
     * @Date: 2018-09-08 18:52:52
     */
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] typeIds) {
        payTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
