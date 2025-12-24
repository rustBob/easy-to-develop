package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class UserCouponPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

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


}
