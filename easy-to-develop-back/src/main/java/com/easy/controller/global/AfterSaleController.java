package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.AfterSale;
import com.easy.entity.dto.AfterSaleDTO;
import com.easy.entity.dto.pg.AfterSalePageQueryDTO;
import com.easy.entity.vo.AfterSaleVO;
import com.easy.mapper.AfterSaleMapper;
import com.easy.service.impl.AfterSaleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("after-sales")
public class AfterSaleController extends BaseController<AfterSale, AfterSaleDTO, AfterSaleVO, AfterSalePageQueryDTO> {
    @Autowired
    public AfterSaleController(AfterSaleServiceImpl service) {
        super("GlobalAfterSaleController", service);
    }
}
