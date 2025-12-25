package com.easy.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.config.SaTokenConfiguration;
import com.easy.entity.User;
import com.easy.entity.dto.LoginDTO;
import com.easy.entity.dto.LogoutDTO;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.vo.LoginVO;
import com.easy.entity.vo.RoleVO;
import com.easy.mapper.UserMapper;
import com.easy.service.AuthService;
import com.easy.util.AESKeyGenerator;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserServiceImpl userService;

    @Resource
    private SaTokenConfiguration saTokenConfiguration;

    /**
     * 普通登录
     * @param loginDTO 登录参数
     * @return LoginVO
     */
    @Override
    public LoginVO doLoginByPassword(LoginDTO loginDTO) throws AppException {
        User u = userMapper.selectOneWithRelationsByQuery(QueryWrapper.create()
                .eq(User::getUsername, loginDTO.getUsername()));

        if(null == u || u.getDeleted() == 1){
            throw new AppException(Status.USER_NOT_EXISTS);
        }

        // 解密密码
        String privateKey;
        try {
            privateKey = AESKeyGenerator.generateKey(AESKeyGenerator.generateSaltByLong(Long.parseLong(String.valueOf(u.getId()))),
                    saTokenConfiguration.getSecretKey());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AppException(Status.FAILED_TO_GET_PRIVATE_KEY);
        }

        // 对比密码
        if(!SaSecureUtil.aesDecrypt(privateKey, u.getPassword())
                .equals(loginDTO.getPassword())){
            log.error("密码错误");
            throw new AppException(Status.PASSWORD_ERROR);
        }

        StpUtil.login(u.getId());
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(u.getRole(), roleVO);

        return LoginVO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .role(roleVO)
                .token(StpUtil.getTokenValue())
                .build();
    }

    /**
     * 手机登录
     * @param loginDTO 登录参数
     * @return LoginVO
     */
    @Override
    public LoginVO doLoginByPhone(LoginDTO loginDTO) throws AppException {
        User u = userMapper.selectOneWithRelationsByQuery(QueryWrapper.create()
                .eq(User::getPhone, loginDTO.getPhone()));

        if(null == u || u.getDeleted() == 1){
            throw new AppException(Status.USER_NOT_EXISTS);
        }
        // TODO 短信验证

        StpUtil.login(u.getId());
        RoleVO roleVO = new RoleVO();
        BeanUtils.copyProperties(u.getRole(), roleVO);

        return LoginVO.builder()
                .id(u.getId())
                .username(u.getUsername())
                .role(roleVO)
                .build();
    }

    /**
     * 登出
     * @param logoutDTO 登出参数
     */
    @Override
    public void doLogout(LogoutDTO logoutDTO) throws AppException {
        StpUtil.logout(logoutDTO.getId());
    }

    /**
     * 注册
     * @param userDTO 注册参数
     */
    @Override
    public void doRegister(UserDTO userDTO) throws AppException {
        if(userDTO.getPhone() != null && !userDTO.getPhone().isEmpty()){
            userDTO.setUsername("用户" + userDTO.getPhone());
        }
        userDTO.setRoleId("1");
        userService.post(userDTO);
    }
}
