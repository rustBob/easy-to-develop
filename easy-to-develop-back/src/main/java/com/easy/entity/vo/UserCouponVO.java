package com.easy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponVO {

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
     * 过期时间
     */
    private Integer expiration;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
