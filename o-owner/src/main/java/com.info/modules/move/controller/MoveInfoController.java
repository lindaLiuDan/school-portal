package com.info.modules.move.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.move.vo.MoveVo;
import com.info.modules.move.service.IMoveInfoService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
@RestController
@RequestMapping("api/moveInfo")
public class MoveInfoController extends AbstractController {

    @Autowired
    private IMoveInfoService moveInfoService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:29
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }


    /**
     * @Description 查询活动列表——app
     * @Author LiuDan
     * @Date 2019/6/9 22:49
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getList")
    public ResultMessage getList(@RequestParam Map<String, Object> params) {
        PageUtils page = moveInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }


    /**
     * @Description 查询活动详情——app
     * @Author LiuDan
     * @Date 2019/6/10 8:26
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getDetail")
    public ResultMessage getDetail(Integer moveId) {
        if (moveId == null) {
            return ResultMessage.error("参数错误！");
        }
        MoveVo detail = moveInfoService.getDetail(moveId);
            return ResultMessage.ok(detail);

    }

}
