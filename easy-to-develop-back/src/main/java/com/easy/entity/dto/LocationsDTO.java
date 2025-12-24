package com.easy.entity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationsDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 用户iD
     */
    private Long userId;

    /**
     * 地址
     */
    private String position;

    /**
     * 详细位置
     */
    private String detail;

    /**
     * 位置标签，家，学校...
     */
    private String tag;
}
