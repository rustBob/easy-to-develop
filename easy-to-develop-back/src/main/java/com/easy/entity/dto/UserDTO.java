package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO{
    /**
     * 主键id
     */
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
     * 邮箱
     */
    private String email;

    /**
     * 会员卡ID
     */
    private String memberCardID;

    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
