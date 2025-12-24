package com.easy.entity.vo;

import com.easy.entity.MemberCard;
import com.easy.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    @Serial
    private final static long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

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
    private Long MemberCardId;

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
     * 是否在线
     */
    private Integer online;

    /**
     * 逻辑删除
     */
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
    /**
     * 角色
     */
    private Role role;

    /**
     * 会员卡
     */
    private MemberCard memberCard;
}
