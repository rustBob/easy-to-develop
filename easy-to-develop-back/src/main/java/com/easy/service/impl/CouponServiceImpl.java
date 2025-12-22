package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Coupon;
import com.easy.entity.dto.CouponDTO;
import com.easy.entity.dto.pg.CouponPageQueryDTO;
import com.easy.entity.vo.CouponVO;
import com.easy.mapper.CouponMapper;
import com.easy.service.CouponService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponServiceImpl extends BaseServiceImpl<Coupon, CouponDTO, CouponVO, CouponPageQueryDTO> implements CouponService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public CouponServiceImpl(CouponMapper mapper) { super(mapper);}

    @Override
    protected void beforePost(CouponDTO couponDTO) throws AppException {
        couponDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
