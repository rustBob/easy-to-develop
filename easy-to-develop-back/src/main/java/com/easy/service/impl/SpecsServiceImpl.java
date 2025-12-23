package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Specs;
import com.easy.entity.dto.SpecsDTO;
import com.easy.entity.dto.pg.SpecsPageQueryDTO;
import com.easy.entity.vo.SpecsVO;
import com.easy.mapper.SpecsMapper;
import com.easy.service.SpecsService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class SpecsServiceImpl extends BaseServiceImpl<Specs, SpecsDTO, SpecsVO, SpecsPageQueryDTO> implements SpecsService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public SpecsServiceImpl(SpecsMapper mapper) {super(mapper);}

    @Override
    protected void beforePost(SpecsDTO specsDTO) throws AppException {
        specsDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
