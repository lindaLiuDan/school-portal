package com.info.modules.provider.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: LiuDan
 * @Date: 2019/7/13 16:16
 * @Description: 商户信息个人中心的内容
 */
@Data
public class ProvideMyselfVo {

    private Integer providerId;

    //商家余额
    private BigDecimal balence;

    //商家名称
    private String userName;

    //商家头像
    private String img;

    private String smallImg;
}
