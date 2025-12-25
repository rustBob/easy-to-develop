package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDTO {

    /**
     * ID
     */
    private String id;

    /**
     * 优惠类型
     */
    private String type;

    /**
     * 优惠额度
     */
    private BigDecimal value;

    /**
     * 名称
     */
    private String name;

    /**
     * 最小使用金额
     */
    private Integer minAmount;

}
