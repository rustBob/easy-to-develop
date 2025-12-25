package com.easy.entity.vo;

import com.easy.entity.Drinks;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreGoodVO {

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

    /**
     * 商品
     */
    private List<Drinks> drinks;
}
