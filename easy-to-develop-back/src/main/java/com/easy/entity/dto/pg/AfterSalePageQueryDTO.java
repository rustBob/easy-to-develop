package com.easy.entity.dto.pg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfterSalePageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 主键id
     */
    private String id;

    /**
     * 售后类型
     */
    private Integer type;

    /**
     * 售后单号
     */
    private String orderId;

    /**
     * 售后描述
     */
    private String description;

    /**
     * 售后状态
     */
    private Integer status;
}
