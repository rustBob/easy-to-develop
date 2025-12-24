package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.AddIns;
import com.easy.entity.dto.AddInsDTO;
import com.easy.entity.dto.pg.AddInsPageQueryDTO;
import com.easy.entity.vo.AddInsVO;
import com.easy.mapper.AddInsMapper;
import com.easy.service.AddInsService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddInsServiceImpl extends BaseServiceImpl<AddIns, AddInsDTO, AddInsVO, AddInsPageQueryDTO> implements AddInsService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public AddInsServiceImpl(AddInsMapper mapper) {super(mapper);}

    @Override
    protected void beforePost(AddInsDTO addInsDTO) throws AppException {
        addInsDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
