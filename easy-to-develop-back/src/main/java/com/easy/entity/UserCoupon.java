package com.easy.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user_coupon")
public class UserCoupon {

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 优惠券ID
     */
    private String couponId;

    /**
     * 是否有效
     */
    private Integer isValid;

    /**
     * 过期时间
     */
    private Integer expiration;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;
}
