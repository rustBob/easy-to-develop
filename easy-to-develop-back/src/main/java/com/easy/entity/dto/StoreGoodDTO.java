package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreGoodDTO {

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
