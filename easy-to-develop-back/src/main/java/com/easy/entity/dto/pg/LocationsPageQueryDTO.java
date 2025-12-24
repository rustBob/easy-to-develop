package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class LocationsPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 用户iD
     */
    private Long userId;

    /**
     * 收货人
     */
    private String name;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 地址
     */
    private String position;
}
