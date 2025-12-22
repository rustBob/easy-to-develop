package com.easy.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesVO {
    /**
     * ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     *
     */
    private String key;
}
