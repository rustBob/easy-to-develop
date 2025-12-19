package com.easy.entity;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("easy_user")
public class User{
    /**
     * 主键id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户性别 （1为男，0为女，-1为未知）
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 是否启用
     */
    @Column(value = "is_enabled")
    private Integer enabled;

    /**
     * 逻辑删除
     */
    @Column(value = "is_deleted", isLogicDelete = true)
    private Integer deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    // ==============================关联表==============================
    @RelationOneToOne(targetField = "id", selfField = "roleId")
    private Role role;
}
