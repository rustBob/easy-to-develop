package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class RolePageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 父级id
     */
    private String parentId;
}
