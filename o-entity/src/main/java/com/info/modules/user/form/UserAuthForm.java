package com.info.modules.user.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/21 13:49
 * @Return:
 */
@Data
public class UserAuthForm {

//    @NotNull(message = "房屋实名认证主键不能为空", groups = {UpdateGroup.class})
    private Integer authId;

    /**
     * 会员ID主键
     */
    @NotNull(message = "会员ID主键", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区小区ID主键", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 社区小区楼层ID主键
     */
    @NotNull(message = "社区小区楼层ID主键", groups = {AddGroup.class, UpdateGroup.class})
    private Integer buildId;
    /**
     * 业主房间编号----------废弃字段
     */
//    @NotNull(message = "业主房间编号", groups = {AddGroup.class, UpdateGroup.class})
//    private String homeNo;
    /**
     * 业主身份类型 1 业主 2 租客 3 业主亲戚 4 其他
     */
    @NotNull(message = "业主身份类型", groups = {AddGroup.class, UpdateGroup.class})
    private Integer identity;
    /**
     * 业主姓名
     */
    @NotNull(message = "业主姓名", groups = {AddGroup.class, UpdateGroup.class})
    private String realName;
    /**
     * 业主身份证号码
     */
    @NotNull(message = "业主身份证号码", groups = {AddGroup.class, UpdateGroup.class})
    private String idCard;
}
