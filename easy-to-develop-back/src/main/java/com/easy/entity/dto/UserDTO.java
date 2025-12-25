package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

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
     * 昵称
     */
    private String nickname;

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
     * 会员卡ID
     */
    private Long memberCardID;

    /**
     * 总积分
     */
    private Integer totalPoints;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 是否启用
     */
    private Integer enabled;

    /**
     * 逻辑删除
     */
    private Integer deleted;

}
