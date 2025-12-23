package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponDTO {

    /**
     * id
     */
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
    private LocalDateTime createTime;
}
