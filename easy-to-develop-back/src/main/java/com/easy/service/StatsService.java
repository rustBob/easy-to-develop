package com.easy.service;

import com.easy.entity.dto.StatsDTO;
import com.easy.entity.vo.StatsVO;

import java.util.List;

public interface StatsService {
    List<StatsVO> getStats(StatsDTO statsDTO);
}
