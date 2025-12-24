package com.easy.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
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
@Table("easy_file")
public class File {
    /**
     * 文件id
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.snowFlakeId)
    private String id;

    /**
     * 上传者id
     */
    private String userId;

    /**
     * 文件名
     */
    private String name;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 文件大小
     */
    private Integer size;

    /**
     * 文件类型
     */
    private String extType;

    /**
     * 上传时间
     */
    private LocalDateTime createTime;

    /**
     * 文件状态
     */
    private Integer status;

    /**
     * 是否公开
     */
    private Integer isPublic;
}
