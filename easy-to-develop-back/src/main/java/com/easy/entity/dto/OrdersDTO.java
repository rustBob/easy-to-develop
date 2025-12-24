package com.easy.entity.dto;

import com.mybatisflex.annotation.Column;
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
public class OrdersDTO {


    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单商品
     */
    private List<OrderItemsDTO> orderItems;

    /**
     * 商店ID
     */
    private Long storeId;

    /**
     * 用户地址
     */
    private Long locationId;

    /**
     * 优惠券ID
     */
    private Long couponId;


    /**
     * 总费用
     */
    private BigDecimal totalAmount;

    /**
     * 最终费用
     */
    private BigDecimal finalAmount;

    /**
     * 优惠券减免金额
     */
    private BigDecimal couponDiscountAmount;

    /**
     * 会员等级减免金额
     */
    private BigDecimal levelDiscountAmount;

    /**
     * 积分抵消金额
     */
    private BigDecimal pointsDeduction;

    /**
     * 积分使用量
     */
    private Integer pointsConsumption;

    /**
     * 订单备注
     */
    private String remarks;


}
