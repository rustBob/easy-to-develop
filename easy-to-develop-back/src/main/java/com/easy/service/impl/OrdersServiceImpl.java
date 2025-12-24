package com.easy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.easy.common.Result;
import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.entity.*;
import com.easy.entity.dto.OrderItemsDTO;
import com.easy.entity.dto.OrdersDTO;
import com.easy.entity.dto.UserDTO;
import com.easy.mapper.OrderItemsMapper;
import com.easy.mapper.OrdersMapper;
import com.easy.mapper.UserMapper;
import com.easy.service.OrderItemsService;
import com.easy.service.OrdersService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@NoArgsConstructor
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrdersService {



    @Resource
    private OrderItemsService orderItemsService;

    @Resource
    private OrderItemsMapper orderItemsMapper;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCouponServiceImpl userCouponService;



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(OrdersDTO ordersDTO) throws AppException {
        User user = userMapper.selectOneById(StpUtil.getLoginIdAsLong());


        List<OrderItemsDTO> orderItems = ordersDTO.getOrderItems();  // 一定要加这行！

        List<String> itemIds = new ArrayList<>();

        for (OrderItemsDTO orderItemsDTO : orderItems) {
            orderItemsService.post(orderItemsDTO);  // 插入一条

            // 直接取当前表里最新的一条记录（开发时基本不会错）
            OrderItems last = orderItemsMapper.selectOneByQuery(
                    QueryWrapper.create()
                            .orderBy("id", false)  // 按 id 降序（雪花ID越大越新）
                            .limit(1)
            );

            if (last != null) {
                itemIds.add(String.valueOf(last.getId()));
            }
        }
        if(!ordersDTO.getCouponId().equals(0L)){
            userCouponService.delete(ordersDTO.getCouponId());
        }

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .roleId(user.getRoleId())
                .username(user.getUsername())
                .avatar(user.getAvatar())
                .sex(user.getSex())
                .phone(user.getPhone())
                .birthday(user.getBirthday())
                .memberCardID(user.getMemberCardId())
                .totalPoints(user.getTotalPoints()-ordersDTO.getPointsConsumption()+ordersDTO.getFinalAmount().intValue()/10)
                .balance(user.getBalance().subtract(ordersDTO.getFinalAmount()))
                .enabled(user.getEnabled())
                .deleted(user.getDeleted())
                .enabled(user.getEnabled())
                .build();

        userService.update(userDTO);


        Random random = new Random();
        Orders orders = Orders.builder()
                .orderType(ordersDTO.getOrderType())
                .userId(user.getId())
                .storeId(ordersDTO.getStoreId())
                .couponId(ordersDTO.getCouponId())
                .locationId(ordersDTO.getLocationId())
                .totalAmount(ordersDTO.getTotalAmount())
                .finalAmount(ordersDTO.getFinalAmount())
                .levelDiscountAmount(ordersDTO.getLevelDiscountAmount())
                .remarks(ordersDTO.getRemarks())
                .orderItemsId(String.join(",", itemIds))
                .estimatedTime(900)
                .pickupCode(random.nextInt(9000)+1000)
                .levelDiscountAmount(new BigDecimal(0))
                .pointsConsumption(ordersDTO.getPointsConsumption())
                .pointsDeduction(ordersDTO.getPointsDeduction())
                .couponDiscountAmount(ordersDTO.getCouponDiscountAmount())
                .status(1)
                .build();


        save(orders);

    }

    @Override
    public Result<String> updateOrder(String id,Integer status) throws AppException {
        Orders orders = getById(id);
        if(orders==null){
            log.error("未找到订单");
            throw new AppException(Status.ORDER_NOT_FOUND);
        }
        orders.setStatus(status);
        updateById(orders);
        return null;
    }
}
