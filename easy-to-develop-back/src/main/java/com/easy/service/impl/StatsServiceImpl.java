package com.easy.service.impl;

import com.easy.entity.Orders;
import com.easy.entity.dto.StatsDTO;
import com.easy.entity.vo.StatsVO;
import com.easy.mapper.OrdersMapper;
import com.easy.mapper.UserMapper;
import com.easy.service.StatsService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mybatisflex.core.query.QueryMethods.count;
import static com.mybatisflex.core.query.QueryMethods.distinct;

@Service
public class StatsServiceImpl implements StatsService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<StatsVO> getStats(StatsDTO statsDTO) {
        double income = 0;
        double orderCount = 0;
        double userCount = userMapper.selectCountByQuery(QueryWrapper.create());
        double activeUserCount;
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusDays(30);
        
        activeUserCount = ordersMapper.selectCountByQuery(QueryWrapper.create()
                .select(count(distinct(Orders::getUserId)))
                .ge(Orders::getCreateTime, oneMonthAgo)
        );

        List<Double> monthOrderCount = new ArrayList<>();
        List<Double> monthIncome = new ArrayList<>();
        Map<String, Double> incomeMap = new HashMap<>();
        Map<String, Double> countMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        QueryWrapper wrapper = QueryWrapper.create()
                .eq("status", 4);

        if(statsDTO.getStoreId() != null){
            wrapper.eq(Orders::getStoreId, statsDTO.getStoreId());
        }

        List<Orders> orders = ordersMapper.selectListByQuery(wrapper);

        if (orders != null) {
            for (Orders order : orders) {
                double amount = order.getFinalAmount() != null ? order.getFinalAmount().doubleValue() : 0.0;
                income += amount;

                if (order.getCreateTime() != null) {
                    String key = order.getCreateTime().format(formatter);
                    incomeMap.put(key, incomeMap.getOrDefault(key, 0.0) + amount);
                    countMap.put(key, countMap.getOrDefault(key, 0.0) + 1.0);
                }
            }
            orderCount = orders.size();
        }

        LocalDate now = LocalDate.now();
        for (int i = 11; i >= 0; i--) {
            String key = now.minusMonths(i).format(formatter);
            monthIncome.add(incomeMap.getOrDefault(key, 0.0));
            monthOrderCount.add(countMap.getOrDefault(key, 0.0));
        }

        return List.of(
                StatsVO.builder().name("orderCount").value(List.of(orderCount)).build(),
                StatsVO.builder().name("totalIncome").value(List.of(income)).build(),
                StatsVO.builder().name("userCount").value(List.of(userCount)).build(),
                StatsVO.builder().name("activeUserCount").value(List.of(activeUserCount)).build(),
                StatsVO.builder().name("monthIncome").value(monthIncome).build(),
                StatsVO.builder().name("monthOrderCount").value(monthOrderCount).build()
        );
    }
}
