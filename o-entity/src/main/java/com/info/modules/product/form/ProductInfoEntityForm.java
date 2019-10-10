package com.info.modules.product.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Data
public class ProductInfoEntityForm extends BaseEntity {

    /**
     * 商品主键
     */
    @TableId
    @NotNull(message = "商品单位ID不能为空", groups = {UpdateGroup.class})
    private Integer productId;
    /**
     * 商品单位信息ID主键
     */
    @NotNull(message = "商品单位ID不能为空", groups = {UpdateGroup.class})
    private Integer unitId;
    /**
     * 商家信息ID主键
     */
    @NotNull(message = "商家信息ID主键不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer providerId;
    /**
     * 商品类别信息ID主键
     */
    @NotNull(message = "商品类别信息ID主键不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer cateId;
    /**
     * 商品编号
     */
    @NotBlank(message = "商品编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String productNo;
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Length(message = "商品名称不能不能大于20个汉字", groups = {AddGroup.class, UpdateGroup.class})
    private String productName;
    /**
     * 商品库存数量
     */
    @NotNull(message = "商品库存数量不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer stock;
    /**
     * 商品主图片
     */
    @NotBlank(message = "商品主图片不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String img;
    /**
     * 商品主图缩略图
     */
    @NotBlank(message = "商品主图缩略图不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String smallImg;
    /**
     * 销售价格
     */
    @NotBlank(message = "销售价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal salesPrice;
    /**
     * 点击数
     */
    private Integer clickNum;
    /**
     * 正常购买该商品送的积分数 默认是送0积分
     */
    private Integer scoreIntegral;
    /**
     * 商品简介
     */
    private String brief;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 商品状态  1 下架的 2 未销售  3 在售
     */
    @NotNull(message = "商品状态不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer status;
    /**
     * 上架时间
     */
    @NotNull(message = "上架时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date onlineTime;
    /**
     * 发布人ID
     */
    @NotNull(message = "发布人ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer creator;
    /**
     * 发布时间
     */
    @NotNull(message = "发布时间不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改时间
     */
    @NotNull(message = "修改时间不能为空", groups = {UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    @NotNull(message = "修改人不能为空", groups = {UpdateGroup.class})
    private Integer editor;
    /**
     * meta关键词 作为SEO优化使用
     */
    private String meta;
    /**
     * 备注
     */
    @Length(message = "商品备注不能不能大于20个字符", groups = {AddGroup.class, UpdateGroup.class})
    private String memo;

}
