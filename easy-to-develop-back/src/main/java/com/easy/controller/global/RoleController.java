package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.Role;
import com.easy.entity.dto.RoleDTO;
import com.easy.entity.dto.RolePageQueryDTO;
import com.easy.entity.vo.RoleVO;
import com.easy.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@GlobalRestController
@RequestMapping("/roles")
public class RoleController extends BaseController<Role, RoleDTO, RoleVO, RolePageQueryDTO> {

    @Autowired
    public RoleController(RoleServiceImpl service) {
        super("GlobalRoleController", service);
    }
}
