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
     * 订单状态(1：制作中，2：制作完成，3：外送中/等待取餐中，4：订单完成，0：订单取消)
     */
    private Integer status;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商店ID
     */
    private String storeId;
}
