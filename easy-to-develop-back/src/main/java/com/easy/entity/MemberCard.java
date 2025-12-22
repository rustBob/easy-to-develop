package com.easy.entity;


import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
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
@Table("member_card")
public class MemberCard {
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
