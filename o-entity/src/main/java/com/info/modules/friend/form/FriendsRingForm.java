package com.info.modules.friend.form;


import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
public class FriendsRingForm {


    /**
     * 所在社区小区ID主键
     */
    @NotNull(message = "社区小区ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 发布人
     */
    @NotNull(message = "发布人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 发布内容
     */
    @NotNull(message = "发布内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    
    //图片
    @NotNull(message = "发布图片不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private MultipartFile[] img;
}
