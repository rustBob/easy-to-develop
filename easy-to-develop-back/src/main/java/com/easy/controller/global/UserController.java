package com.easy.controller.global;

import cn.dev33.satoken.annotation.SaIgnore;
import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.controller.BaseController;
import com.easy.entity.User;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.dto.UserPageQueryDTO;
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

    /**
     * 添加用户
     * @param data 用户数据
     * @return Result<UserVO>
     * @throws AppException 自定义异常
     */
    @Override
    @SaIgnore
    public Result<String> post(UserDTO data) throws AppException {
        return super.post(data);
    }
}
