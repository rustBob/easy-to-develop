package com.easy.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItems {

    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 饮品ID
     */
    private String drinkId;

    /**
     * 饮品数量
     */
    private Integer drinksQuantity;

    /**
     * 是否添加小料
     */
    private Integer ifAdd;

    /**
     * 小料ID
     */
    private String addInsId;

    /**
     * 小料数量
     */
    private Integer addInsQuantity;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 饮品规格选项
     */
    private String specs;
}
