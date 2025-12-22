package com.easy.entity;

import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("banners")
public class Banner {

    /**
     * id
     */
    private String id;

    /**
     * 图片路径
     */
    private String img;
}
