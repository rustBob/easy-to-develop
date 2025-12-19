package com.easy.entity;

import com.easy.entity.vo.RoleVO;
import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("easy_admin_menu")
public class AdminMenu {
    /**
     * 主键
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
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
    @Column(value = "is_visible")
    private Integer visible;

    //=======================关联表=======================
    /**
     * 所属角色
     */
    @RelationOneToOne(
            targetTable = "easy_role",
            targetField = "id", selfField = "roleId"
    )
    private RoleVO role;
}
