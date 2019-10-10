package com.info.modules.move.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MoveInfoSignForm implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 社区活动ID主键
     */
    @NotNull(message = "社区活动ID主键", groups = {AddGroup.class, UpdateGroup.class})
    private Integer moveId;

    //用户id
    @NotNull(message = "报名人ID主键", groups = {AddGroup.class, UpdateGroup.class})
    private  Integer userId;
    /**
     * 报名人名称
     */
    @NotNull(message = "报名人名称", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;
    /**
     * 报名人电话
     */
    @NotNull(message = "报名人电话", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
}
