package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Categories;
import com.easy.entity.dto.CategoriesDTO;
import com.easy.entity.dto.pg.CategoriesPageQueryDTO;
import com.easy.entity.vo.CategoriesVO;
import com.easy.mapper.CategoriesMapper;
import com.easy.service.CategoriesService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriesServiceImpl extends BaseServiceImpl<Categories, CategoriesDTO, CategoriesVO, CategoriesPageQueryDTO> implements CategoriesService {

    @Autowired
    public CategoriesServiceImpl(CategoriesMapper mapper){super(mapper);}

}
