package com.easy.service.impl;

import com.easy.entity.AdminMenu;
import com.easy.entity.dto.AdminMenuDTO;
import com.easy.entity.dto.pg.AdminMenuPageQueryDTO;
import com.easy.entity.vo.AdminMenuVO;
import com.easy.mapper.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenu, AdminMenuDTO, AdminMenuVO, AdminMenuPageQueryDTO>{

    @Autowired
    public AdminMenuServiceImpl(AdminMenuMapper mapper) {
        super(mapper);
    }
}
