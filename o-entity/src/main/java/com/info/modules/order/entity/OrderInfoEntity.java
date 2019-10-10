package com.info.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.modules.area.entity.UserAddressEntity;
import com.info.modules.product.vo.ProductInfoVO;
import com.info.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
@TableName("order_info")
public class OrderInfoEntity extends BaseEntity {


    /**
     * 订单主键ID编号
     */
    @TableId
    private Integer orderId;
    /**
     * 订单流程跟踪表ID主键
     */
    private Integer flowId;
    /**
     * flowName
     */
    @TableField(exist = false)
    private String flowName;
    /**
     * 收货地址ID主键
     */
//    @NotNull(message = "收货地址ID不能为空", groups = {AddGroup.class})
    private Integer addressId;
    /**
     * 会员ID主键
     */
//    @NotNull(message = "会员ID不能为空", groups = {AddGroup.class})
    private Integer userId;
    /**
     * 商家信息ID主键
     */
    @NotNull(message = "会员ID不能为空", groups = {AddGroup.class})
    private Integer providerId;
    /**
     * 商家名称
     */
    @TableField(exist = false)
    private String providerName;
    /**
     * 订单NO编号
     */
    private String orderNo;
    /**
     * 订单标题
     */
//    @Length(message = "订单标题长度不能超过15个汉字", groups = {AddGroup.class, UpdateGroup.class})
    private String title;
    /**
     * 会员邮件
     */
//    @Email(message = "会员邮件格式不正确", groups = {AddGroup.class})
    private String email;
    /**
     * 订单备注说明
     */
//    @Length(message = "订单备注说明超过32个汉字", groups = {AddGroup.class})
    private String memo;
    /**
     * 订单来源 1 pc端订单 2 线下订单 3 App端订单 4 团购 5 抢购 6 APP端积分兑换 7 批发商订单 8 微信端订单
     */
    private Integer orderSource;
    /**
     * 支付类型 1 微信 2 支付宝 3 银联 4 苹果内购 5 谷歌支付
     */
    private Integer payType;
    /**
     * 支付金额（实际支付金额）
     */
    private BigDecimal totalMoney;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    /**
     * 订单创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    /**
     * 订单服务时间--针对的是服务这块的订单
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTime;
    /**
     * 使用积分数
     */
    private Integer useCoin;
    /**
     * 订单获赠积分数
     */
    private Integer orderCoin;
    /**
     * 订单完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completeTime;
    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipTime;
    /**
     * 是否需发票联 1 需要  0 不需要
     */
//    @NotNull(message = "不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer isInvoice;
    /**
     * 发票主题
     */
//    @NotNull(message = "不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String invoiceTitle;
    /**
     * 发票主题文本
     */
//    @NotNull(message = "不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String invoiceInfo;
    /**
     * 是否已经拆分  0 否  1 是  默认0
     */
    private Integer isSplit;
    /**
     * 0 已删除 1 正常
     */
    private Integer isDel;
    /**
     * 订单详细信息
     */
    @TableField(exist = false)
    private List<OrderInfoDetailEntity> detailEntityList = new ArrayList<OrderInfoDetailEntity>();

    /**
     * 订单详细信息
     */
    @TableField(exist = false)
    private UserAddressEntity userAddressEntity;
    /**
     * 订单详细信息
     */
    @TableField(exist = false)
    private List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();


}
