package com.easy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminMenuVO implements Serializable {

    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String parentId;

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

    /**
     * 所属角色
     */
    private RoleVO role;
}
