package com.easy.controller.admin;

import com.easy.annotation.AdminRestController;
import com.easy.controller.BaseController;
import com.easy.entity.AdminMenu;
import com.easy.entity.dto.AdminMenuDTO;
import com.easy.entity.dto.pg.AdminMenuPageQueryDTO;
import com.easy.entity.vo.AdminMenuVO;
import com.easy.service.impl.AdminMenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@AdminRestController
@RequestMapping("/menus")
public class AdminMenuController extends BaseController<AdminMenu, AdminMenuDTO, AdminMenuVO, AdminMenuPageQueryDTO> {

    @Autowired
    public AdminMenuController(AdminMenuServiceImpl service) {
        super("AdminMenuController", service);
    }
}
