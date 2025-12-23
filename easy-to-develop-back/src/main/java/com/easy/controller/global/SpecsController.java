package com.easy.controller.global;


import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Specs;
import com.easy.entity.dto.SpecsDTO;
import com.easy.entity.dto.pg.SpecsPageQueryDTO;
import com.easy.entity.vo.SpecsVO;
import com.easy.service.impl.SpecsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/specs")
public class SpecsController extends BaseController<Specs, SpecsDTO, SpecsVO, SpecsPageQueryDTO> {
    @Autowired
    public SpecsController(SpecsServiceImpl service){super("globalSpecsController",service);}

}
