package com.info.modules.area.address;

import com.info.modules.area.entity.UserAddressEntity;
import com.info.modules.area.service.IUserAddressService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 功能描述: App接口端用户收货地址信息的接口
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/12 18:42
 * @Return:
 */
@RestController
@RequestMapping("address/info")
public class UserAddressController extends AbstractController {

    /**
     * 功能描述:
     *
     * @param: 收貨地址信息业务实现层
     * @auther: 高山溪 By User
     * @date: 2018/11/5 18:19
     */
    @Autowired
    private IUserAddressService userAddressService;


    /**
     * 功能描述: 分頁获取自己所有收貨地址信息--------暂时废弃
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 13:32
     * @Return:
     */
    @GetMapping("list")
    @RequiresPermissions("address:list")
    public ResultMessage pageList(@RequestParam Map<String, Object> params) {
        PageUtils page = userAddressService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: app端无分页获取自己所有收貨地址信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 18:40
     * @Return:
     */
    @GetMapping("all")
    @RequiresPermissions("address:list")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        Assert.isNull(params.get("userId"), "用户ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(userAddressService.all(params));
    }

    /**
     * 功能描述: 添加收货地址 每個用戶只有個收貨地址
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 18:40
     * @Return:
     */
    @PostMapping("save")
    @RequiresPermissions("address:save")
    public ResultMessage save(@RequestBody @Valid UserAddressEntity userAddressEntity) {
        ValidatorUtils.validateEntity(userAddressEntity, ConfigConstant.ERROR, AddGroup.class);
        return userAddressService.insertAddress(userAddressEntity);
    }

    /**
     * 功能描述: 根据用户ID收货地址ID查询地址详情
     *
     * @auther: By User
     * @param:
     * @date: 2018/11/14 11:58
     */
    @GetMapping("info/{addressId}")
    @RequiresPermissions("address:info")
    public ResultMessage get(@PathVariable("addressId") Integer addressId) {
        Assert.isNull(addressId, "地址ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(userAddressService.selectByIdEntity(addressId));
    }

    /**
     * 功能描述: 修改收貨地址
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 18:41
     * @Return:
     */
    @PostMapping("update")
    @RequiresPermissions("address:update")
    public ResultMessage update(@RequestBody @Valid UserAddressEntity userAddressEntity) {
        ValidatorUtils.validateEntity(userAddressEntity, ConfigConstant.ERROR, AddGroup.class);
        return userAddressService.updateAddressById(userAddressEntity);
    }

    /**
     * 功能描述: app端用户刪除收貨地址
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 18:41
     * @Return:
     */
    @PostMapping("del/{addressId}")
    @RequiresPermissions("address:del")
    public ResultMessage del(@PathVariable("addressId") Integer addressId) {
        Assert.isNull(addressId, "地址ID不能为空", ConfigConstant.ERROR);
        return userAddressService.del(addressId);
    }


}


