package com.easy.service.impl;

import com.easy.entity.Drinks;
import com.easy.entity.dto.DrinksDTO;
import com.easy.entity.dto.pg.DrinksPageQueryDTO;
import com.easy.entity.vo.DrinksVO;
import com.easy.mapper.DrinksMapper;
import com.easy.service.DrinksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinksServiceImpl extends BaseServiceImpl<Drinks, DrinksDTO, DrinksVO, DrinksPageQueryDTO> implements DrinksService {
    @Autowired
    public DrinksServiceImpl(DrinksMapper mapper) { super(mapper);}


}
