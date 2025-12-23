package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class StorePageQueryDTO {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;
}
