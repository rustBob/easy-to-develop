package com.easy.service.impl;

import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.entity.AddIns;
import com.easy.entity.Drinks;
import com.easy.entity.OrderItems;
import com.easy.entity.dto.OrderItemsDTO;
import com.easy.entity.dto.pg.OrderItemsPageQueryDTO;
import com.easy.entity.vo.OrderItemsVO;
import com.easy.mapper.AddInsMapper;
import com.easy.mapper.DrinksMapper;
import com.easy.mapper.OrderItemsMapper;
import com.easy.service.OrderItemsService;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@NoArgsConstructor
public class OrderItemsServiceImpl extends BaseServiceImpl<OrderItems, OrderItemsDTO, OrderItemsVO, OrderItemsPageQueryDTO> implements OrderItemsService {

    @Resource
    private DrinksServiceImpl drinksService;

    @Resource
    private AddInsServiceImpl addInsService;

    @Autowired
    public OrderItemsServiceImpl(OrderItemsMapper mapper) {super(mapper);}

    @Override
    protected void beforePost(OrderItemsDTO orderItemsDTO) throws AppException {
        String drinkId = orderItemsDTO.getDrinkId();
        String addInsId = orderItemsDTO.getAddInsId();

        Drinks drink = drinksService.getOne(QueryWrapper.create().eq(Drinks::getId,drinkId));
        AddIns addIns = addInsService.getOne(QueryWrapper.create().eq(AddIns::getId,addInsId));

        if (drink == null || addIns == null) {
            log.error("drink or add_ins is null");
            throw new AppException(Status.PRODUCT_NOT_FOUND);
        }
    }
}
