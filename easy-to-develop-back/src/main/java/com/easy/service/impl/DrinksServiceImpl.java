package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.Drinks;
import com.easy.entity.StoreGood;
import com.easy.entity.dto.DrinksDTO;
import com.easy.entity.dto.pg.DrinksPageQueryDTO;
import com.easy.entity.vo.DrinksVO;
import com.easy.mapper.DrinksMapper;
import com.easy.service.DrinksService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DrinksServiceImpl extends BaseServiceImpl<Drinks, DrinksDTO, DrinksVO, DrinksPageQueryDTO> implements DrinksService {

    @Autowired
    public DrinksServiceImpl(DrinksMapper mapper){super(mapper);}

    @Override
    protected Page<Drinks> doList(DrinksPageQueryDTO query) throws AppException {
        Page<Drinks> page = new Page<>(query.getPageNum(), query.getPageSize());

        QueryWrapper queryWrapper = QueryWrapper.create(new Drinks()).as("g")
                .leftJoin(StoreGood.class).as("sg").on("g.id = sg.good_id");

        if(query.getStoreId() != null && !query.getStoreId().equals("0") ){
            queryWrapper.where("sg.store_id = ?", query.getStoreId());
        }

        return mapper.paginateWithRelations(page, queryWrapper);
    }

    @Override
    protected List<Drinks> doGet(DrinksDTO drinksDTO) throws AppException {
        QueryWrapper queryWrapper = QueryWrapper.create(new Drinks()).as("g")
                .leftJoin(StoreGood.class).as("sg").on("g.id = sg.good_id");

        if(drinksDTO.getStoreId() != null && !drinksDTO.getStoreId().equals("0")){
            queryWrapper.where("sg.store_id = ?", drinksDTO.getStoreId());
        }

        return mapper.selectListWithRelationsByQuery(queryWrapper);
    }
}
