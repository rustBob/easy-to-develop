package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    /**
     * 主键
     */
    private String id;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     */
    private String key;
}
