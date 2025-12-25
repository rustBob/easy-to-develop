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
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 种类
     */
    private String categoryId;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 描述
     */
    private String description;
}
