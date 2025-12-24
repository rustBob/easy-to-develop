package com.easy.entity.vo;

import com.easy.entity.OrderItems;
import com.easy.entity.Store;
import com.easy.entity.User;
import com.easy.entity.UserCoupon;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.RelationManyToOne;
import com.mybatisflex.annotation.RelationOneToMany;
import com.mybatisflex.annotation.RelationOneToOne;
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
public class OrdersVO {

    /**
     * id
     */
    private Long id;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 商店ID
     */
    private Long storeId;

    /**
     * 商店名称
     */
    private String storeName;

    /**
     * 商店地址
     */
    private String storeAddress;

    /**
     * 订单具体商品
     */
    private OrderItems orderItems;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 订单状态(1：制作中，2：制作完成，3：外送中/等待取餐中，4：订单完成，0：订单取消)
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private LocalDateTime createTime;

    /**
     * 取餐码
     */
    private Integer pickupCode;

    /**
     * 预计制作时间
     */
    private Integer estimatedTime;

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
