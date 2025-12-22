package com.easy.entity.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberCardVO {
    /**
     * id
     */
    private Integer id;

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

}
