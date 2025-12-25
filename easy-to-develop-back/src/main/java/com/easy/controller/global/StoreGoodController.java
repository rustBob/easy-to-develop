package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.controller.BaseController;
import com.easy.entity.StoreGood;
import com.easy.entity.dto.StoreGoodDTO;
import com.easy.entity.dto.pg.StoreGoodPageQueryDTO;
import com.easy.entity.vo.StoreGoodVO;
import com.easy.service.impl.StoreGoodServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@GlobalRestController
@RequestMapping("/store-goods")
public class StoreGoodController extends BaseController<StoreGood, StoreGoodDTO, StoreGoodVO, StoreGoodPageQueryDTO> {

    @Autowired
    private StoreGoodServiceImpl service;

    @Autowired
    public StoreGoodController(StoreGoodServiceImpl service) {
        super("GlobalStoreGoodController", service);
    }
}
