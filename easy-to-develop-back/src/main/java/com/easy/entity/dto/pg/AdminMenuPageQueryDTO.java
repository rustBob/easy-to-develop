package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class AdminMenuPageQueryDTO {
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

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单状态
     */
    private Integer visible;
}
