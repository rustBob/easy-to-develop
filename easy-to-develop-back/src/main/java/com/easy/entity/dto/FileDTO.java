package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    /**
     * 文件id
     */
    private Long id;

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
