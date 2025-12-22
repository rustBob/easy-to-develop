package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DrinksDTO {
    /**
     * 名称
     */
    private String name;

    /**
     * 种类
     */
    private String category;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;
}
