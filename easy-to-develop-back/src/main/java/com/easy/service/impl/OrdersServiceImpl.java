package com.easy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.entity.*;
import com.easy.entity.dto.OrderItemsDTO;
import com.easy.entity.dto.OrdersDTO;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.dto.pg.OrdersPageQueryDTO;
import com.easy.entity.vo.OrdersVO;
import com.easy.mapper.OrdersMapper;
import com.easy.mapper.UserMapper;
import com.easy.service.OrderItemsService;
import com.easy.service.OrdersService;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@NoArgsConstructor

public class OrdersServiceImpl extends BaseServiceImpl<Orders,OrdersDTO, OrdersVO, OrdersPageQueryDTO> implements OrdersService {

    @Resource
    private OrderItemsService orderItemsService;

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCouponServiceImpl userCouponService;

    @Autowired
    public OrdersServiceImpl(OrdersMapper mapper){super(mapper);}



    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(OrdersDTO ordersDTO) throws AppException {
        User user = userMapper.selectOneById(StpUtil.getLoginIdAsLong());


        String orderId = String.valueOf(snowflakeDistributeIdUtil.nextId());
        List<OrderItemsDTO> orderItems = ordersDTO.getOrderItems();

        for (OrderItemsDTO orderItemsDTO : orderItems) {
            orderItemsDTO.setOrderId(orderId);
            orderItemsService.post(orderItemsDTO);
        }
        if(!ordersDTO.getCouponId().equals("0")){
            userCouponService.delete(Long.valueOf(ordersDTO.getCouponId()));
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
                .id(orderId)
                .orderType(ordersDTO.getOrderType())
                .userId(user.getId())
                .storeId(ordersDTO.getStoreId())
                .couponId(ordersDTO.getCouponId())
                .totalAmount(ordersDTO.getTotalAmount())
                .finalAmount(ordersDTO.getFinalAmount())
                .levelDiscountAmount(ordersDTO.getLevelDiscountAmount())
                .remarks(ordersDTO.getRemarks())
                .estimatedTime(900)
                .pickupCode(random.nextInt(9000)+1000)
                .levelDiscountAmount(new BigDecimal(0))
                .pointsConsumption(ordersDTO.getPointsConsumption())
                .pointsDeduction(ordersDTO.getPointsDeduction())
                .couponDiscountAmount(ordersDTO.getCouponDiscountAmount())
                .status(1)
                .build();

        if (ordersDTO.getOrderType().equals("pickup"))
            orders.setLocationId("0");
        else
            orders.setLocationId(ordersDTO.getLocationId());


        save(orders);

    }

    @Override
    public OrdersVO convertToVO(Orders entity) throws AppException {
        // 1. 空值校验：先判断传入的entity是否为null
        if (entity == null) {
            log.warn("转换VO时，订单实体为null");
            return null;
        }

        // 2. 查询关联数据
        Orders orders = mapper.selectOneWithRelationsByQuery(
                QueryWrapper.create().eq(Orders::getId, entity.getId())
        );

        // 3. 对查询结果做空值校验
        if (orders == null) {
            log.error("根据订单ID{}未查询到关联数据", entity.getId());
            throw new AppException(Status.ORDER_NOT_FOUND);
        }

        // 4. 调用父类转换方法
        OrdersVO ordersVO = super.convertToVO(orders);

        // 5. 关联对象空值校验：User
        if (orders.getUser() != null) {
            ordersVO.setUsername(orders.getUser().getUsername());
        } else {
            ordersVO.setUsername("未知用户"); // 给默认值，避免VO字段为null
            log.warn("订单{}的关联用户不存在（userId={}）", orders.getId(), orders.getUserId());
        }

        // 6. 关联对象空值校验：Store（核心修复点）
        if (orders.getStore() != null) {
            ordersVO.setStoreName(orders.getStore().getName());
            ordersVO.setStoreAddress(orders.getStore().getAddress());
        } else {
            ordersVO.setStoreName("未知商店"); // 给默认值
            ordersVO.setStoreAddress("未知地址");
            log.warn("订单{}的关联商店不存在（storeId={}）", orders.getId(), orders.getStoreId());
        }

        if (orders.getLocations() != null) {
            ordersVO.setAddress(orders.getLocations().getPosition()+"\n"+orders.getLocations().getDetail());
        } else {
            ordersVO.setAddress("该订单为自提");
            log.warn("订单{}的关联商店不存在（storeId={}）", orders.getId(), orders.getStoreId());
        }

        return ordersVO;
    }

    @Override
    public void updateOrder(String id,Integer status) throws AppException {
        Orders orders = getById(id);
        if(orders==null){
            log.error("未找到订单");
            throw new AppException(Status.ORDER_NOT_FOUND);
        }
        orders.setStatus(status);
        updateById(orders);
    }
}
