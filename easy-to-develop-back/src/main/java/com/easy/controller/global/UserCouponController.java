package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.entity.dto.UserCouponDTO;
import com.easy.service.UserCouponService;
import com.easy.service.impl.UserCouponServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("user-coupons")
public class UserCouponController {

    @Resource
    private UserCouponServiceImpl userCouponService;

    @PostMapping
    public Result<String> post(@RequestBody UserCouponDTO userCouponDTO) throws AppException {
        log.info("用户获取优惠券 ---- dto：{}", userCouponDTO);
        userCouponService.post(userCouponDTO);
        return Result.success();
    }

    @PutMapping("/use")
    public Result<String> update(@RequestBody UserCouponDTO userCouponDTO) throws AppException {
        log.info("用户使用优惠券 ---- dto：{}", userCouponDTO);
        userCouponService.update(userCouponDTO);
        return Result.success();
    }
}
