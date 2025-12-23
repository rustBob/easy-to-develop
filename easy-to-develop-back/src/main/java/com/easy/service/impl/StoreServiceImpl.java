package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Store;
import com.easy.entity.dto.StoreDTO;
import com.easy.entity.dto.pg.StorePageQueryDTO;
import com.easy.entity.vo.StoreVO;
import com.easy.mapper.StoreMapper;
import com.easy.service.StoreService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class StoreServiceImpl extends BaseServiceImpl<Store, StoreDTO, StoreVO, StorePageQueryDTO> implements StoreService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public StoreServiceImpl(StoreMapper mapper){super(mapper);}

    @Override
    protected void beforePost(StoreDTO storeDTO) throws AppException {
        storeDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
