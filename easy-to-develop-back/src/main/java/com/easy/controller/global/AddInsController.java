package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.AddIns;
import com.easy.entity.dto.AddInsDTO;
import com.easy.entity.dto.pg.AddInsPageQueryDTO;
import com.easy.entity.vo.AddInsVO;
import com.easy.service.impl.AddInsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/add-ins")
public class AddInsController extends BaseController<AddIns, AddInsDTO, AddInsVO, AddInsPageQueryDTO> {
    @Autowired
    public AddInsController(AddInsServiceImpl service){super("globalAddInsController",service);}
}
