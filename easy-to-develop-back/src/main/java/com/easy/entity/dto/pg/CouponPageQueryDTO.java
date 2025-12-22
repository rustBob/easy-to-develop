package com.easy.entity.dto.pg;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 优惠类型
     */
    private String type;

    /**
     * 优惠额度
     */
    private BigDecimal value;

    /**
     * 名称
     */
    private String name;

}
