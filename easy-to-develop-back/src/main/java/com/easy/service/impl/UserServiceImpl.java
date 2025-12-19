package com.easy.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.easy.common.Status;
import com.easy.common.exception.AppException;
import com.easy.config.SaTokenConfiguration;
import com.easy.entity.User;
import com.easy.entity.dto.UserDTO;
import com.easy.entity.dto.UserPageQueryDTO;
import com.easy.entity.vo.UserVO;
import com.easy.mapper.UserMapper;
import com.easy.service.UserService;
import com.easy.util.AESKeyGenerator;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@NoArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<User, UserDTO, UserVO, UserPageQueryDTO> implements UserService {

    @Resource
    private SaTokenConfiguration saTokenConfiguration;

    @Resource
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        super(mapper);
    }


    /**
     * 添加用户前的逻辑（密码加密）
     * @param userDTO 用户信息
     */
    @Override
    protected void beforePost(UserDTO userDTO) throws AppException {
        User u = getOne(QueryWrapper.create().eq(User::getUsername, userDTO.getUsername()));

        if(null != u){
            log.error("用户已存在 ---- {}", u);
            throw new AppException(Status.USER_EXISTS);
        }

        // 加密
        // 1.获取密钥
        String id = snowflakeDistributeIdUtil.nextId().toString();
        userDTO.setId(id);

        byte[] salt = AESKeyGenerator.generateSaltByLong(Long.parseLong(id));
        String privateKey;

        try {
            privateKey = AESKeyGenerator.generateKey(salt, saTokenConfiguration.getSecretKey());
        } catch (Exception e) {
            log.error("获取密钥失败 ---- {}", e.getMessage());
            throw new AppException(Status.FAILED_TO_GET_PRIVATE_KEY);
        }

        // 2.加密
        userDTO.setPassword(SaSecureUtil.aesEncrypt(privateKey, userDTO.getPassword()));
    }

    @Override
    protected void beforeUpdate(UserDTO userDTO) throws AppException {
        User u = getOne(QueryWrapper.create().eq(User::getId, userDTO.getId()));

        String password = userDTO.getPassword();
        if(!password.isEmpty()){
            // 加密
            // 1.获取密钥
            byte[] salt = AESKeyGenerator.generateSaltByLong(Long.parseLong(u.getId()));
            String privateKey;

            try {
                privateKey = AESKeyGenerator.generateKey(salt, saTokenConfiguration.getSecretKey());
            } catch (Exception e) {
                log.error("获取密钥失败 ---- {}", e.getMessage());
                throw new AppException(Status.FAILED_TO_GET_PRIVATE_KEY);
            }

            // 2.加密
            userDTO.setPassword(SaSecureUtil.aesEncrypt(privateKey, userDTO.getPassword()));
        }
    }
}
