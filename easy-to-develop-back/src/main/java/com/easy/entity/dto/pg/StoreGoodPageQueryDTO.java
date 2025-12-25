package com.easy.entity.dto.pg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreGoodPageQueryDTO {
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
     * 门店id
     */
    private String storeId;

    /**
     * 商品id
     */
    private String goodId;

    /**
     * 是否启售
     */
    private Integer sale;
}
