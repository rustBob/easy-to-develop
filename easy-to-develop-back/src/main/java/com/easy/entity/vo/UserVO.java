package com.easy.entity.vo;

import com.easy.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
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
    private String id;

    /**
     * 用户名
     */
    private String username;

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
    private Integer enabled;

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
    private Role role;
}
