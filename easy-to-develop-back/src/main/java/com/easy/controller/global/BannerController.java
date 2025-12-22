package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Banner;
import com.easy.entity.dto.BannerDTO;
import com.easy.entity.dto.pg.BannerPageQueryDTO;
import com.easy.entity.vo.BannerVO;
import com.easy.service.impl.BannerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;


@Slf4j
@GlobalRestController
@RequestMapping("/banner")
public class BannerController extends BaseController<Banner, BannerDTO, BannerVO, BannerPageQueryDTO> {
    @Autowired
    public BannerController(BannerServiceImpl service){super("globalBannerController", service);}
}
