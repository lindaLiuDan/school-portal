package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;
/**
 * 系统消息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-16 15:01:31
 */
@Data
@TableName("sys_message_info")
public class SysMessageInfoEntity extends BaseEntity {


            /**
         * 系统消息ID主键
         */
                @TableId
            private Integer messageId;
            /**
         * 消息标题
         */
            private String title;
            /**
         * 消息内容
         */
            private String content;
            /**
         * 推送者 0 代表系统推送
         */
            private Integer accountId;
            /**
         * 社区ID主键
         */
            private Integer infoId;
            /**
         * 发布时间
         */
            private Date creatorTime;
            /**
         * 是否已读 0 未读 1 已读
         */
            private Integer isRead;
            /**
         * 是否删除 0 已删除 1 未删除
         */
            private Integer isDel;
    
}
