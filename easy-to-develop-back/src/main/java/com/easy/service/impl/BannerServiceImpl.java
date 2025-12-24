package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Banner;
import com.easy.entity.dto.BannerDTO;
import com.easy.entity.dto.pg.BannerPageQueryDTO;
import com.easy.entity.vo.BannerVO;
import com.easy.mapper.BannerMapper;
import com.easy.service.BannerService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerServiceImpl extends BaseServiceImpl<Banner, BannerDTO, BannerVO, BannerPageQueryDTO> implements BannerService {
    @Autowired
    public BannerServiceImpl(BannerMapper mapper){super(mapper);}
}
