package com.easy.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("role")
public class Role {
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
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     */
    private String key;
}
