package com.easy.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemsVO {

    /**
     * id
     */
    private Long id;

    /**
     * 饮品ID
     */
    private Long drinkId;

    /**
     * 饮品数量
     */
    private Integer drinksQuantity;

    /**
     * 小料ID
     */
    private Long addInsId;

    /**
     * 小料数量
     */
    private Integer addInsQuantity;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 饮品规格选项
     */
    private String specs;
}
