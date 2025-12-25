package com.easy.entity;

import com.mybatisflex.annotation.*;
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
@Table("store_good")
public class StoreGood {

    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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
    @Column(value = "is_sale")
    private Integer sale;

    /**
     * 商品
     */
    @RelationOneToMany(selfField = "goodId",targetField = "id")
    private List<Drinks> drinks;
}
