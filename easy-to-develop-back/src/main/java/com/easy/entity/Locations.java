package com.easy.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("locations")
public class Locations {

    /**
     * id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 用户iD
     */
    private String userId;

    /**
     * 地址
     */
    private String position;

    /**
     * 详细位置
     */
    private String detail;

    /**
     * 位置标签，家，学校...
     */
    private String tag;
}
