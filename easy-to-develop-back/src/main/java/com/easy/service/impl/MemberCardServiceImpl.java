package com.easy.service.impl;

import com.easy.entity.MemberCard;
import com.easy.entity.dto.MemberCardDTO;
import com.easy.entity.dto.pg.MemberCardPageQueryDTO;
import com.easy.entity.vo.MemberCardVO;
import com.easy.mapper.MemberCardMapper;
import com.easy.service.MemberCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCardServiceImpl extends BaseServiceImpl<MemberCard, MemberCardDTO, MemberCardVO, MemberCardPageQueryDTO> implements MemberCardService {

    @Autowired
    public MemberCardServiceImpl(MemberCardMapper mapper) { super(mapper);}
}
