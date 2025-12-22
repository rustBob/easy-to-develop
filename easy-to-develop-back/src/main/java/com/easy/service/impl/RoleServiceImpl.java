package com.easy.service.impl;

import com.easy.entity.Role;
import com.easy.entity.dto.RoleDTO;
import com.easy.entity.dto.pg.RolePageQueryDTO;
import com.easy.entity.vo.RoleVO;
import com.easy.service.RoleService;
import com.mybatisflex.core.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, RoleDTO, RoleVO, RolePageQueryDTO> implements RoleService {

    @Autowired
    public RoleServiceImpl(BaseMapper<Role> mapper) {
        super(mapper);
    }
}
