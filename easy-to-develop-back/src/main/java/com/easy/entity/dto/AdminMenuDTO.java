package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 所属角色id
     */
    private String roleId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单排序
     */
    private Integer order;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 菜单状态
     */
    private Integer visible;
}
