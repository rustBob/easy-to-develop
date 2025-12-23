package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Locations;
import com.easy.entity.dto.LocationsDTO;
import com.easy.entity.dto.pg.LocationsPageQueryDTO;
import com.easy.entity.vo.LocationsVO;
import com.easy.mapper.LocationsMapper;
import com.easy.service.LocationsService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LocationsServiceImpl extends BaseServiceImpl<Locations, LocationsDTO, LocationsVO, LocationsPageQueryDTO> implements LocationsService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public LocationsServiceImpl(LocationsMapper mapper) {super(mapper);}

    @Override
    protected void beforePost(LocationsDTO locationsDTO) throws AppException {
        locationsDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
