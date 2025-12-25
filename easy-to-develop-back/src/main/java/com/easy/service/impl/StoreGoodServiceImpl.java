package com.easy.service.impl;

import com.easy.entity.StoreGood;
import com.easy.entity.dto.StoreGoodDTO;
import com.easy.entity.dto.pg.StoreGoodPageQueryDTO;
import com.easy.entity.vo.StoreGoodVO;
import com.easy.mapper.StoreGoodMapper;
import com.easy.service.StoreGoodService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreGoodServiceImpl extends BaseServiceImpl<StoreGood, StoreGoodDTO, StoreGoodVO, StoreGoodPageQueryDTO> implements StoreGoodService {
    @Autowired
    StoreGoodServiceImpl(StoreGoodMapper mapper){
        super(mapper);
    }

    public void deleteGood(StoreGoodDTO dto){
        mapper.deleteByQuery(QueryWrapper.create()
                .eq(StoreGood::getGoodId, dto.getGoodId())
                .eq(StoreGood::getStoreId, dto.getStoreId())
        );
    }
}
