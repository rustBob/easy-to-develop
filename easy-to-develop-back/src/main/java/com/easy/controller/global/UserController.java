package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.controller.BaseController;
import com.easy.entity.User;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.dto.pg.UserPageQueryDTO;
import com.easy.entity.vo.UserVO;
import com.easy.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

@GlobalRestController
@RequestMapping("/users")
public class UserController extends BaseController<User, UserDTO, UserVO, UserPageQueryDTO> {

    @Autowired
    public UserController(UserServiceImpl userService) {
        super("GlobalUserController", userService);
    }

}
