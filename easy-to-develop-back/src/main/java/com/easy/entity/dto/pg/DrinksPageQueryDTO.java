package com.easy.entity.dto.pg;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DrinksPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;
    /**
     * 名称
     */
    private String name;

    /**
     * 种类
     */
    private Long categoryId;

    /**
     * 商品库存
     */
    private Integer stock;

}
