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
@Table("orders")
public class Orders {

    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 商店ID
     */
    private String storeId;

    /**
     * 订单具体商品ID
     */
    private String orderItemsId;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 地址ID
     */
    private String locationId;

    /**
     * 订单状态(1：制作中，2：制作完成，3：外送中/等待取餐中，4：订单完成，0：订单取消)
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    @Column(onInsertValue = "now()")
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

    //=========================关联表=============================

    @RelationManyToOne(selfField = "userId",targetField = "id")
    private User user;
    
    @RelationManyToOne(selfField = "storeId", targetField = "id")
    private Store store;

    @RelationOneToMany(selfField = "orderItemId", targetField = "id")
    private OrderItems orderItems;

    @RelationOneToOne(selfField = "couponId",targetField = "id")
    private UserCoupon coupon;

    @RelationManyToOne(selfField = "locationId",targetField = "id")
    private Locations locations;




}
