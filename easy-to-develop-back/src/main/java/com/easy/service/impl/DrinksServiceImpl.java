package com.easy.service.impl;

import com.easy.common.Status;
import com.easy.common.constant.ClassPropertyAndMethodConst;
import com.easy.common.exception.AppException;
import com.easy.entity.Drinks;
import com.easy.entity.dto.DrinksDTO;
import com.easy.entity.dto.pg.DrinksPageQueryDTO;
import com.easy.entity.vo.DrinksVO;
import com.easy.mapper.DrinksMapper;
import com.easy.service.DrinksService;
import com.easy.util.AssembleParamsUtil;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DrinksServiceImpl extends BaseServiceImpl<Drinks, DrinksDTO, DrinksVO, DrinksPageQueryDTO> implements DrinksService {

    @Autowired
    public DrinksServiceImpl(DrinksMapper mapper){super(mapper);}

}
