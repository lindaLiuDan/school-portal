package com.info.modules.friend.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserRingLikeVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String img;

    private String userId;

    private String userName;
}
