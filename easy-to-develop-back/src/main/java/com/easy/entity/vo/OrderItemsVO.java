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
    private String id;

    /**
     * 所属订单
     */
    private String orderId;

    /**
     * 饮品ID
     */
    private String drinkId;

    /**
     * 饮品数量
     */
    private Integer drinksQuantity;

    /**
     * 饮品名称
     */
    private String name;

    /**
     * 饮品图片
     */
    private String image;

    /**
     * 饮品描述
     */
    private String description;

    /**
     * 总价
     */
    private BigDecimal price;

    /**
     * 饮品规格选项
     */
    private String specs;
}
