package com.easy.service;

import com.easy.common.exception.AppException;
import com.easy.entity.dto.LoginDTO;
import com.easy.entity.dto.LogoutDTO;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.vo.LoginVO;

public interface AuthService {
    LoginVO doLoginByPassword(LoginDTO loginDTO) throws AppException;

    LoginVO doLoginByPhone(LoginDTO loginDTO) throws AppException;

    void doLogout(LogoutDTO logoutDTO) throws AppException;

    void doRegister(UserDTO userDTO) throws AppException;
}
