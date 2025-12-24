package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.OrderItems;
import com.easy.entity.dto.OrderItemsDTO;
import com.easy.entity.dto.pg.OrderItemsPageQueryDTO;
import com.easy.entity.vo.OrderItemsVO;
import com.easy.service.impl.OrderItemsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/order-items")
public class OrderItemsController extends BaseController<OrderItems, OrderItemsDTO, OrderItemsVO, OrderItemsPageQueryDTO>{
    @Autowired
    public OrderItemsController(OrderItemsServiceImpl service){super("globalOrderItemsController",service);}
}
