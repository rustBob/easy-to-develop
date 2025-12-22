package com.easy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannerVO {
    /**
     * id
     */
    private String id;

    /**
     * 图片路径
     */
    private String img;
}
