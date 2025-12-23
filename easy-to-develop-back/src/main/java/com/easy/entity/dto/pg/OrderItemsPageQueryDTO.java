package com.easy.entity.dto.pg;

import lombok.Data;

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
     * 饮品ID
     */
    private String drinkId;

    /**
     * 小料ID
     */
    private String addInsId;
}
