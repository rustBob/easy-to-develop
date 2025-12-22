package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.MemberCard;
import com.easy.entity.dto.MemberCardDTO;
import com.easy.entity.dto.pg.MemberCardPageQueryDTO;
import com.easy.entity.vo.MemberCardVO;
import com.easy.service.impl.MemberCardServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/member-card")
public class MemberCardController extends BaseController<MemberCard, MemberCardDTO, MemberCardVO, MemberCardPageQueryDTO> {
    @Autowired
    public MemberCardController(MemberCardServiceImpl service){ super("GlobalMemberCardController", service); };
}
