package com.easy.entity;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private String id;

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
    private String categoryId;

    /**
     * 图片路径
     */
    private String image;

    /**
     * 描述
     */
    private String description;


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

    /**
     * 商品规格
     */
    @RelationManyToMany(
            joinTable = "good_specs",
            selfField = "id", joinSelfColumn = "good_id",
            targetField = "id", joinTargetColumn = "specs_id"
    )
    private List<Specs> specs;
}
