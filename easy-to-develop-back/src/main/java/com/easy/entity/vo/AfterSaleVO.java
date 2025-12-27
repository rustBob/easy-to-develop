package com.easy.entity.vo;

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
public class AfterSaleVO {

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

    /**
     * 售后图片
     */
    private List<String> images;
}
