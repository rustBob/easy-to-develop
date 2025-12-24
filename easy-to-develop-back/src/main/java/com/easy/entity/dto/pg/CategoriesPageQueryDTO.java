package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class CategoriesPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     *
     */
    private String key;
}
