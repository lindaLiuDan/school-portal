package com.info.modules.advertis.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.modules.advertis.service.IAdvertisInfoService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 社区小区广告信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@RestController
@RequestMapping("api/advertisInfo")
public class AdvertisInfoController extends AbstractController {

    @Autowired
    private IAdvertisInfoService advertisInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = advertisInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页查询所有的信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/11 15:34
     * @Return:
     */
    @Login
    @RequestMapping("/all/{infoId}")
    public ResultMessage all(@PathVariable("infoId") Integer infoId) {
        return ResultMessage.ok(advertisInfoService.all(infoId));
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @GetMapping("/info/{advertisId}")
    public ResultMessage info(@PathVariable("advertisId") Integer advertisId) {
        AdvertisInfoEntity advertisInfo = advertisInfoService.getById(advertisId);
        return ResultMessage.ok(advertisInfo);
    }

}
