package com.info.modules.feedback.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * 功能描述: 提交我的意见反馈信息的表单
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/12 17:54
 */
@Data
@ApiModel(value = "添加收货地址表单")
public class SaveAppFeedbackForm {


    /**
     * 反馈人
     */
    @ApiModelProperty(value = "用户ID主键")
    @NotNull(message = "用户ID主键不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 反馈问题内容
     */
    @ApiModelProperty(value = "反馈内容")
    @NotNull(message = "反馈内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String content;

    @ApiModelProperty(value = "电话号码")
    private String mobail;


    @ApiModelProperty(value = "图片")
    private MultipartFile img;

}
