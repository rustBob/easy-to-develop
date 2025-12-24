package com.easy.entity;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("drinks")
public class Drinks {

    /**
     * ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 种类
     */
    private Long categoryId;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 描述
     */
    private String description;

    /**
     * 商品库存
     */
    private Integer stock;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updateTime;

    //===========================关联表================================

    @RelationManyToOne(selfField = "categoryId",targetField = "id")
    private Categories category;

}
