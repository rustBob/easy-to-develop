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
public class RoleVO implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     */
    private String key;
}
