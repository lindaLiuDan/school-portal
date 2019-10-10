package com.info.modules.move.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class MoveVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer moveId;

    private String title;

    private String img;

    private String smallImg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    private BigDecimal price;

    private String content;


}
