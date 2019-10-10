package com.info.modules.pay.controller;

import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.pay.entity.PayLogEntity;
import com.info.modules.pay.service.IPayLogService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 支付日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-09-08 13:57:51
 */
@RestController
@RequestMapping("api/payLog")
public class PayLogController extends AbstractController {

    @Autowired
    private IPayLogService payLogService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = payLogService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{logId}")
    public ResultMessage info(@PathVariable("logId") Integer logId) {
        PayLogEntity payLog = payLogService.getById(logId);
        return ResultMessage.ok(payLog);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResultMessage save(@RequestBody PayLogEntity payLog) {
        ValidatorUtils.validateEntity(payLog);
//        payLog.setCreator(getUserId().intValue());
        payLog.setCreatorTime(DateUtils.now());
        payLogService.save(payLog);
        return ResultMessage.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultMessage update(@RequestBody PayLogEntity payLog) {
        ValidatorUtils.validateEntity(payLog);
        payLogService.updateById(payLog);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] logIds) {
        payLogService.removeByIds(Arrays.asList(logIds));
        return ResultMessage.ok();
    }

}
