package com.easy.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriesDTO {
    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     *
     */
    private String key;
}
