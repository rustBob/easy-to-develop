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
@Table("categories")
public class Categories {

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
