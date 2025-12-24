package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.controller.BaseController;
import com.easy.entity.Orders;
import com.easy.entity.dto.OrdersDTO;
import com.easy.entity.dto.pg.OrdersPageQueryDTO;
import com.easy.entity.vo.OrdersVO;
import com.easy.service.OrdersService;
import com.easy.service.impl.OrdersServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/orders")
public class OrdersController{
    @Resource
    private OrdersService ordersService;

    @PostMapping("/make-order")
    public Result<String> makeOrder(@RequestBody OrdersDTO dto) throws AppException {
        ordersService.createOrder(dto);
        return Result.success();
    }

    @PutMapping("/update")
    public Result<String> updateStatus(String id,Integer status) throws AppException {
        ordersService.updateOrder(id,status);
        return Result.success();
    }

}
