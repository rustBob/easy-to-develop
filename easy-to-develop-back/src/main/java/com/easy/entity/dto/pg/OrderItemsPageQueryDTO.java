package com.easy.entity.dto.pg;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemsPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 所属订单
     */
    private String orderId;

    /**
     * 饮品ID
     */
    private String drinkId;

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
}
