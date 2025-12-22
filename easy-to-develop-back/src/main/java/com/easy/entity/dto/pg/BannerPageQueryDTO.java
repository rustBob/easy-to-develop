package com.easy.entity.dto.pg;

import lombok.Data;

@Data
public class BannerPageQueryDTO {
    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页大小
     */
    private Integer pageSize;

}
