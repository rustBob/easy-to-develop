package com.easy.entity.dto;

import lombok.Data;


@Data
public class UserPageQueryDTO{

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

    /**
     * 角色id
     */
    private String role_id;

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
}
