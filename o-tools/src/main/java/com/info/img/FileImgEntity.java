package com.info.img;

import lombok.Data;

import java.io.Serializable;

/**
 * @Auther: 高山溪
 * @Date: 2019/6/24 14:23
 * @Description:
 */
@Data
public class FileImgEntity implements Serializable {

    /**
     * 正常的图片
     */
    private String img;
    /**
     * 缩略图
     */
    private String smallImg;
}
