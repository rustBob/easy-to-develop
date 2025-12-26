package com.easy.controller.admin;

import com.easy.annotation.AdminRestController;
import com.easy.entity.dto.StatsDTO;
import com.easy.entity.vo.StatsVO;
import com.easy.service.StatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@AdminRestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping
    public List<StatsVO> get(StatsDTO statsDTO){
        log.info("StatsController:执行get方法，查询条件 ---- data：{}", statsDTO);
        return statsService.getStats(statsDTO);
    }
}
