package com.easy.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类包名以及后缀常量
 */
@Getter
@AllArgsConstructor
public enum ClassPackageNameAndSuffixConst {
    VO("com.easy.entity.vo", "VO"),
    DTO("com.easy.entity.dto", "DTO"),
    ENTITY("com.easy.entity", "");

    private final String packageName;
    private final String suffix;
}
