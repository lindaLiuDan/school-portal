package com.info.modules.order.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
public class SaveOrderInfoForm extends BaseEntity {

    /**
     * 订单流程跟踪表ID主键
     */
//    @NotNull(message = "订单流程ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer flowId;
    /**
     * 收货地址ID主键
     */
    @NotNull(message = "收货地址ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer addressId;
    /**
     * 会员ID主键
     */
    @NotNull(message = "会员ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 商品productIds分割后的数组 ["productId,数量,cartId";"productIds,数量,cartId";"productIds,数量,cartId"]
     */
    @NotNull(message = "商品ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String productId;
    /**
     * 商家信息ID主键
     */
    @NotNull(message = "商家ID不能为空", groups = {AddGroup.class})
    private Integer providerId;
    /**
     * 订单类型  1.美食订单 2.便民服务订单
     */
    @NotNull(message = "订单类型orderType不能为空", groups = {AddGroup.class})
    private Integer orderType;
    /**
     * 订单服务时间--针对的是服务这块的订单
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTime;
    /**
     * 订单标题
     */
    @Length(message = "订单标题字数不能大于15个汉字", min = 1, max = 15, groups = {AddGroup.class})
    private String title;
    /**
     * 支付类型 1 微信 2 支付宝 3 银联 4 苹果内购 5 谷歌支付
     */
    @NotNull(message = "支付类型ID不能为空", groups = {AddGroup.class})
    private Integer payType;
    /**
     * 支付金额（实际支付金额）
     */
//    @NotNull(message = "支付金额不能为空", groups = {AddGroup.class})
    private BigDecimal totalMoney;
    /**
     * 订单备注说明
     */
    @Length(message = "订单备注字数不能大于32个汉字", min = 1, max = 32, groups = {AddGroup.class})
    private String memo;
    /**
     * 订单类型 1 pc端订单 2 线下订单 3 App端订单 4 团购 5 抢购 6 APP端积分兑换 7 批发商订单 8 微信端订单
     */
    @NotNull(message = "订单类型不能为空", groups = {AddGroup.class})
    private Integer orderSource;
    /**
     * 订单创建时间
     */
//    @NotNull(message = "订单创建时间不能为空", groups = {AddGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
    /**
     * 使用积分数
     */
    private Integer useCoin;
    /**
     * 是否需发票联 1 需要  0 不需要
     */
    private Integer isInvoice;
    /**
     * 发票主题
     */
    @Length(message = "发票主题不能大于20个汉字", min = 1, max = 20, groups = {AddGroup.class})
    private String invoiceTitle;
    /**
     * 发票主题文本
     */
    @Length(message = "发票主题文本不能大于32个汉字", min = 1, max = 32, groups = {AddGroup.class})
    private String invoiceInfo;


}
