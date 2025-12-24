package com.easy.service;

import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.entity.Orders;
import com.easy.entity.dto.OrdersDTO;
import com.mybatisflex.core.service.IService;

public interface OrdersService extends IService<Orders> {

    void createOrder(OrdersDTO ordersDTO) throws AppException;

    Result<String> updateOrder(String id,Integer status) throws AppException;

}
