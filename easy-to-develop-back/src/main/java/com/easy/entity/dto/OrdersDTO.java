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
     * id
     */
    private String id;

    /**
     * 订单状态(1：制作中，2：制作完成，3：外送中/等待取餐中，4：订单完成，0：订单取消)
     */
    private Integer status;

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
    private String storeId;

    /**
     * 用户地址
     */
    private String locationId;

    /**
     * 优惠券ID
     */
    private String couponId;


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
