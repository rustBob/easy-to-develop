package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.entity.dto.LoginDTO;
import com.easy.entity.dto.LogoutDTO;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.vo.LoginVO;
import com.easy.service.AuthService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 登录
     * @param loginDTO 登录参数
     * @return Result<LoginVO>
     * @throws AppException 自定义应用异常
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO loginDTO) throws AppException {
        log.info("登录 ---- dto：{}", loginDTO);
        return Result.success(loginDTO.getPhone() == null || loginDTO.getPhone().isEmpty() ?
                authService.doLoginByPassword(loginDTO) : authService.doLoginByPhone(loginDTO));
    }

    /**
     * 登出
     * @return Result<Void>
     */
    @PostMapping("/logout")
    public Result<String> logout(@RequestBody LogoutDTO logoutDTO) throws AppException{
        log.info("登出 ---- dto:{}", logoutDTO);
        authService.doLogout(logoutDTO);
        return Result.success();
    }

    /**
     * 注册
     * @param userDTO 注册参数
     * @return Result<String>
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody UserDTO userDTO) throws AppException {
        log.info("注册 ---- dto:{}", userDTO);
        authService.doRegister(userDTO);
        return Result.success();
    }
}
