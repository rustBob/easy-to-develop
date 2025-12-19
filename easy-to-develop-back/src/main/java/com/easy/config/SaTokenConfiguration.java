package com.easy.config;

import cn.dev33.satoken.SaManager;
import cn.dev33.satoken.stp.StpInterface;
import com.easy.entity.Role;
import com.easy.entity.User;
import com.easy.mapper.RoleMapper;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
@Getter
public class SaTokenConfiguration implements StpInterface {

    @Value("${sa-token.secret-key}")
    private String secretKey;

    @Resource
    private RoleMapper roleMapper;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String roleKey = SaManager.getSaTokenDao().get("cstoken:loginId-find-role:" + loginId);

        if(roleKey ==  null) {
            Role role = roleMapper.selectOneByQuery(QueryWrapper.create().leftJoin(User.class)
                    .on(Role::getId, User::getRoleId)
                    .eq(User::getId, loginId));

            roleKey = role.getKey();
            SaManager.getSaTokenDao().set("cstoken:loginId-find-role:" + loginId, roleKey, 60 * 60 * 24 * 30);
        }

        return List.of(roleKey);
    }
}
