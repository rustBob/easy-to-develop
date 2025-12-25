package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class OrdersPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * id
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商店ID
     */
    private String storeId;
}
