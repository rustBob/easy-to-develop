package com.easy.entity.vo;

import com.mybatisflex.annotation.Column;
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
public class CouponVO {
    /**
     * ID
     */
    private Long id;

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
     * 数量
     */
    private Integer minAmount;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
