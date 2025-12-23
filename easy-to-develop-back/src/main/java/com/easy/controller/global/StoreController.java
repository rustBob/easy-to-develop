package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Store;
import com.easy.entity.dto.StoreDTO;
import com.easy.entity.dto.pg.StorePageQueryDTO;
import com.easy.entity.vo.StoreVO;
import com.easy.service.impl.StoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@GlobalRestController
@RequestMapping("/stores")
public class StoreController extends BaseController<Store, StoreDTO, StoreVO, StorePageQueryDTO> {
    @Autowired
    public StoreController(StoreServiceImpl service){super("globalStoreController",service);}
}
