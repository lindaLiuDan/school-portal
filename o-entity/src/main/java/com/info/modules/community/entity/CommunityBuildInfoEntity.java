package com.info.modules.community.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区楼房信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 18:36:11
 */
@Data
@TableName("community_build_info")
public class CommunityBuildInfoEntity extends BaseEntity {


    /**
     * 社区楼房信息ID主键
     */
    @TableId
    private Integer buildId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 社区楼号楼层单元名称
     */
    private String bname;
    /**
     * 父类ID 如果是0表示的是楼号最顶级的
     */
    private Integer parentId;
    /**
     * 社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房间号
     */
    private Integer buildType;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 创建时间
     */
    private Date creatorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 物业放行时间
     */
    private Date editorTime;
    /**
     * 排序数
     */
    private Integer sort;
    /**
     * 社区名字
     *
     * */
    @TableField(exist = false)
    private String infoName;
    /**
     * 社区名字
     *
     * */
    @TableField(exist = false)
    private String floorName;
    /**
     * 社区名字
     *
     * */
    @TableField(exist = false)
    private String unitName;
    /**
     * 社区名字
     *
     * */
    @TableField(exist = false)
    private String levelName;
    /**
     * 社区名字
     *
     * */
    @TableField(exist = false)
    private String roomName;

}
