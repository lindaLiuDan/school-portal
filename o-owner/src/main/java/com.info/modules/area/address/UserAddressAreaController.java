package com.info.modules.area.address;

import com.info.common.base.AbstractController;
import com.info.modules.area.entity.UserAddressAreaEntity;
import com.info.modules.area.service.IUserAddressAreaService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 收货地址区域
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/12 18:43
 * @Return:
 */
@RestController
@RequestMapping("api/area")
public class UserAddressAreaController extends AbstractController {


    @Autowired
    private IUserAddressAreaService userAddressAreaService;


    /**
     * 功能描述: 查询所有注册会员用户
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 19:13
     * @Return:
     */
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = userAddressAreaService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页查询城市列表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 19:05
     * @Return:
     */
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<UserAddressAreaEntity> list = userAddressAreaService.all(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 单个收货地址区域
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 19:10
     * @Return:
     */
    @GetMapping("/info/{areaId}")
    public ResultMessage info(@PathVariable("areaId") Integer areaId) {
        UserAddressAreaEntity userAddressAreaEntity = userAddressAreaService.getAreaById(areaId);
        return ResultMessage.ok(userAddressAreaEntity);
    }

    /**
     * 功能描述: 根据areaId查询对应的下一级区域的集合
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 19:05
     * @Return:
     */
    @GetMapping("/getAreaList/{areaId}")
    public ResultMessage getAreaList(@PathVariable("areaId") Integer areaId) {
        Assert.isNull(areaId, "城市code码不能为空",ConfigConstant.ERROR);
        List<UserAddressAreaEntity> list = userAddressAreaService.getAreaList(areaId);
        return ResultMessage.ok(list);
    }

}
