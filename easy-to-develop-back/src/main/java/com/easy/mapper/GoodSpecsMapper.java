package com.easy.mapper;

import com.easy.entity.GoodSpecs;
import com.mybatisflex.core.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodSpecsMapper extends BaseMapper<GoodSpecs> {
}
