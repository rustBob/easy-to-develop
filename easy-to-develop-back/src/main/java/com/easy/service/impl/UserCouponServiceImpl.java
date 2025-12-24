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

    }



}
