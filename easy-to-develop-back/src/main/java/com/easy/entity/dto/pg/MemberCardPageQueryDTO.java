package com.easy.entity.dto.pg;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MemberCardPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 会员等级
     */
    private int memberLevel;

    /**
     * 会员等级名
     */
    private String cardName;

    /**
     * 会员等级标识
     */
    private String key;

    /**
     * 折扣
     */
    private BigDecimal discount;
}
