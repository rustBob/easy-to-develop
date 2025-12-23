package com.easy.entity;

import com.mybatisflex.annotation.*;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("user")
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
     * 头像
     */
    private String avatar;

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
     * 生日
     */
    private Date birthday;

    /**
     * 会员卡等级ID
     */
    private Integer MemberCardId;

    /**
     * 总积分
     */
    private Integer totalPoints;

    /**
     * 余额
     */
    private Integer balance;

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
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(onInsertValue = "now()",onUpdateValue = "now()")
    private LocalDateTime updateTime;


    // ==============================关联表==============================
    /**
     * 角色表
     */
    @RelationOneToOne(targetField = "id", selfField = "roleId")
    private Role role;

    /**
     * 会员卡表
     */
    @RelationOneToOne(targetField = "id", selfField = "MemberCardId")
    private MemberCard memberCard;
}
