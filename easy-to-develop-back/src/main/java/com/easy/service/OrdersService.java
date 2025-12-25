package com.easy.service;

import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.entity.Orders;
import com.easy.entity.dto.OrdersDTO;
import com.easy.entity.dto.pg.OrdersPageQueryDTO;
import com.easy.entity.vo.OrdersVO;
import com.mybatisflex.core.service.IService;

//public interface OrdersService extends IService<Orders> {
public interface OrdersService extends BaseService<Orders, OrdersDTO, OrdersVO, OrdersPageQueryDTO> {
    void createOrder(OrdersDTO ordersDTO) throws AppException;

    void updateOrder(String id,Integer status) throws AppException;

}
