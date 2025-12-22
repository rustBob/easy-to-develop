package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.controller.BaseController;
import com.easy.entity.Drinks;
import com.easy.entity.dto.DrinksDTO;
import com.easy.entity.dto.pg.DrinksPageQueryDTO;
import com.easy.entity.vo.DrinksVO;
import com.easy.service.impl.DrinksServiceImpl;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/drinks")
public class DrinksController extends BaseController<Drinks,DrinksDTO,DrinksVO,DrinksPageQueryDTO> {
    @Autowired
    public DrinksController(DrinksServiceImpl service){ super("GlobalDrinksController", service); }
}