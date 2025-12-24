package com.easy.service.impl;

import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.entity.Coupon;
import com.easy.entity.User;
import com.easy.entity.UserCoupon;
import com.easy.entity.dto.UserCouponDTO;
import com.easy.entity.dto.pg.UserCouponPageQueryDTO;
import com.easy.entity.vo.UserCouponVO;
import com.easy.entity.vo.UserVO;
import com.easy.mapper.UserCouponMapper;
import com.easy.service.UserCouponService;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@NoArgsConstructor
public class UserCouponServiceImpl extends BaseServiceImpl<UserCoupon, UserCouponDTO, UserCouponVO, UserCouponPageQueryDTO> implements UserCouponService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private CouponServiceImpl couponService;

    @Autowired
    public  UserCouponServiceImpl(UserCouponMapper mapper) {
        super(mapper);
    }



    @Override
    protected void beforePost(UserCouponDTO userCouponDTO) throws AppException {
        User user = userService.getOne(QueryWrapper.create().eq(User::getId, userCouponDTO.getUserId()));
        if (user == null) {
            log.error("用户不存在");
            throw new AppException(Status.USER_NOT_EXISTS);
        }
        Coupon coupon = couponService.getOne(QueryWrapper.create().eq(Coupon::getId,userCouponDTO.getCouponId()));
        if (coupon == null) {
            log.error("不存在此类型优惠券");
            throw new AppException(Status.COUPON_TYPE_NOT_EXIST);
        }

        userCouponDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }


    @Override
    protected void beforeUpdate(UserCouponDTO userCouponDTO) throws AppException {
        UserCoupon coupon = getOne(QueryWrapper.create().eq(UserCoupon::getId,userCouponDTO.getId()));
        if (coupon == null) {
            log.error("用户不存在此优惠券");
            throw new AppException(Status.COUPON_NOT_FOUND);
        }
        BeanUtils.copyProperties(coupon,userCouponDTO);
        LocalDateTime expireTime = userCouponDTO.getCreateTime().plusSeconds(userCouponDTO.getExpiration());
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(expireTime)){
            log.error("优惠券已过期");
            throw new AppException(Status.OUT_OF_TIME);
        }


        userCouponDTO.setIsValid(0);
    }
}
