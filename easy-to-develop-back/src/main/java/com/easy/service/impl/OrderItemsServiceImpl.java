package com.easy.service.impl;

import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.entity.Drinks;
import com.easy.entity.OrderItems;
import com.easy.entity.dto.OrderItemsDTO;
import com.easy.entity.dto.pg.OrderItemsPageQueryDTO;
import com.easy.entity.vo.OrderItemsVO;
import com.easy.mapper.OrderItemsMapper;
import com.easy.service.OrderItemsService;
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

    @Autowired
    public OrderItemsServiceImpl(OrderItemsMapper mapper) {super(mapper);}

    @Override
    protected void beforePost(OrderItemsDTO orderItemsDTO) throws AppException {
        String drinkId = orderItemsDTO.getDrinkId();

        Drinks drink = drinksService.getOne(QueryWrapper.create().eq(Drinks::getId,drinkId));
        if (drink == null) {
            log.error("未查询到对应饮品");
            throw new AppException(Status.PRODUCT_NOT_FOUND);
        }
    }
}
