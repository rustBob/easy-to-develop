package com.easy.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("transaction")
public class Transaction {

    /**
     * ID
     */
    private String id;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 优惠券ID
     */
    private String couponId;
    /**
     * 总价
     */
    private BigDecimal totalPrice;
    /**
     * 最终价格
     */
    private BigDecimal realPrice;
    /**
     * 描述
     */
    private String description;
    /**
     * 时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime dateTime;
}
