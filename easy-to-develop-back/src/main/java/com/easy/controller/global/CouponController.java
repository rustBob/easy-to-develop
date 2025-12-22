package com.easy.controller.global;


import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Coupon;
import com.easy.entity.dto.CouponDTO;
import com.easy.entity.dto.pg.CouponPageQueryDTO;
import com.easy.entity.vo.CouponVO;
import com.easy.service.impl.CouponServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/coupon")
public class CouponController extends BaseController<Coupon, CouponDTO, CouponVO, CouponPageQueryDTO> {
    @Autowired
    public CouponController(CouponServiceImpl service){super("globalCouponController",service);}
}
