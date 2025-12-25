package com.easy.entity.vo;

import com.easy.entity.User;
import com.mybatisflex.annotation.RelationOneToOne;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreVO {

    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 运营状态
     */
    private String status;

    /**
     * 营业时间
     */
    private String hours;

    /**
     * 管理员ID
     */
    private String userId;

    /**
     * 管理员
     */
    private User admin;
}

