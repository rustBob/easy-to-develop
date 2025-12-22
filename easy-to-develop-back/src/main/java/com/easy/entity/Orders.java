package com.easy.entity;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Orders {
    private String id;
    private String userId;
    private Integer drinkId;
    private String size;
    private Integer drinkAmount;
    private Integer addInsId;
    private Integer addInsAmount;
    private String sugarLevel;
    private String temperature;
    private BigDecimal totalPrice;
}
