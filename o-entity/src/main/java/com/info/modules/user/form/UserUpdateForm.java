package com.info.modules.user.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class UserUpdateForm {

    @NotNull(message = "用户ID不能为空")
    private Integer userId;

    @NotNull(message = "用户名不能为空")
    private String userName;


    private MultipartFile img;

    /**
     * 用户性别 0 女 1 男
     */
    private Integer sex;

}
