package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
@Data
@TableName("user_info")
public class UserInfoEntity extends BaseEntity {


    /**
     * 会员ID主键
     */
    @TableId
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 会员昵称
     */
    private String nickName;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 是否VIP会员 0 否 1 是
     */
    private Integer isVip;
    /**
     * 用户密码
     */
    private String pwd;
    /**
     * 会员个性签名
     */
    private String sign;
    /**
     * 会员状态 0 冻结 1 正常
     */
    private Integer status;
    /**
     * 用户积分
     */
    private Integer integral;
    /**
     * 用户余额
     */
    private BigDecimal balance;
    /**
     * 支付密码
     */
    private String payPwd;
    /**
     * 用户性别 0 女 1 男
     */
    private Integer sex;
    /**
     * APP头像
     */
    private String img;
    /**
     * 头像的压缩图片
     */
    private String smallImg;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date regTime;
    /**
     * 登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;
    /**
     * 上次登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;
    /**
     * 我的邀请码
     */
    private Integer inviteCode;
    /**
     * 微信号
     */
    private String weixin;
    /**
     * 微信用户唯一标示
     */
    private String openid;
    /**
     * 编辑者
     */
    private Integer editor;
    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 0 已删除 1 未删除
     */
    private Integer del;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 维度
     */
    private String latitude;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 输入三次支付密码错误冻结 提示修该支付密码 0冻结1正常
     */
    private Integer frozen;


    //所在小区的名称
    @TableField(exist = false)
    private String infoName;

    //所在小区的id
    @TableField(exist = false)
    private Integer infoId;

}
