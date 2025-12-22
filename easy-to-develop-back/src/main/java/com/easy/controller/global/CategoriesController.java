package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Categories;
import com.easy.entity.dto.CategoriesDTO;
import com.easy.entity.dto.pg.CategoriesPageQueryDTO;
import com.easy.entity.vo.CategoriesVO;
import com.easy.service.impl.CategoriesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/categories")
public class CategoriesController extends BaseController<Categories, CategoriesDTO, CategoriesVO, CategoriesPageQueryDTO> {
    @Autowired
    public CategoriesController(CategoriesServiceImpl service){super("globalCategoriesController",service);}
}
