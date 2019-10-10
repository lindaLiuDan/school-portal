package com.info.modules.move.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.move.form.MoveInfoSignForm;
import com.info.modules.move.service.IMoveInfoSignupService;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
@RestController
@RequestMapping("api/moveInfoSignup")
public class MoveInfoSignupController extends AbstractController {

    @Autowired
    private IMoveInfoSignupService moveInfoSignupService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-06 18:05:23
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        Integer page = moveInfoSignupService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }


    /**
     * @Description 活动报名————app
     * @Author LiuDan
     * @Date 2019/6/22 17:09
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/saveSign")
    public ResultMessage saveSign(MoveInfoSignForm form) {
        if (form == null) {
            return ResultMessage.error("参数错误！");
        }
        return moveInfoSignupService.saveSign(form);
    }


    /**
     * @Description 个人中心  我报名参加的活动
     * @Author LiuDan
     * @Date 2019/6/17 15:47
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getMyJoinMove")
    public ResultMessage getMyJoinMove(@RequestParam Map<String, Object> params) {
        return moveInfoSignupService.getMyJoinMove(params);
    }

}
