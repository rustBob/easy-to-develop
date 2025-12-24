package com.easy.service.impl;

import com.easy.common.exception.AppException;
import com.easy.entity.MemberCard;
import com.easy.entity.dto.MemberCardDTO;
import com.easy.entity.dto.pg.MemberCardPageQueryDTO;
import com.easy.entity.vo.MemberCardVO;
import com.easy.mapper.MemberCardMapper;
import com.easy.service.MemberCardService;
import com.easy.util.SnowflakeDistributeIdUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCardServiceImpl extends BaseServiceImpl<MemberCard, MemberCardDTO, MemberCardVO, MemberCardPageQueryDTO> implements MemberCardService {

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public MemberCardServiceImpl(MemberCardMapper mapper) { super(mapper);}

    @Override
    protected void beforePost(MemberCardDTO memberCardDTO) throws AppException {
        memberCardDTO.setId(String.valueOf(snowflakeDistributeIdUtil.nextId()));
    }
}
