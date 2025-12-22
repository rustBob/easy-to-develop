package com.easy.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.easy.annotation.AdminRestController;
import com.easy.annotation.GeneralRestController;
import com.easy.annotation.GlobalRestController;
import com.easy.common.constant.RoleKeyConst;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
@Getter
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${api.admin.prefix}")
    private String adminPrefix;

    @Value("${api.general.prefix}")
    private String generalPrefix;

    @Value("${api.global.prefix}")
    private String globalPrefix;

    /**
     * 添加路径前缀
     * @param configurer 路径匹配配置器
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix(adminPrefix, c -> c.isAnnotationPresent(AdminRestController.class));
        configurer.addPathPrefix(generalPrefix, c -> c.isAnnotationPresent(GeneralRestController.class));
        configurer.addPathPrefix(globalPrefix, c -> c.isAnnotationPresent(GlobalRestController.class));
    }

    /**
     * 配置跨域映射
     * @param registry 跨域注册器
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("开始全局配置跨域...");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 允许的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的方法
                .allowedHeaders("*") // 允许所有请求头
                .allowCredentials(true); // 允许携带凭证
    }

    /**
     * 添加拦截器
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor( handler -> {
            // 预检不做处理
            SaRouter.match(SaHttpMethod.OPTIONS).back();

            // 登录校验 （放开登录、登出、注册接口）
            SaRouter.match("/**")
                    .notMatch(globalPrefix + "/auth/login")
                    .notMatch(globalPrefix + "/auth/logout")
                    .notMatch(globalPrefix + "/auth/register")
                    .check(StpUtil::checkLogin);

            // 权限校验
            SaRouter.match(adminPrefix + "/**")
                    .notMatch(adminPrefix + "/menus/**")
                    .check(r -> StpUtil.checkRoleOr(RoleKeyConst.ADMIN));
        }).isAnnotation(false) // 关闭注解鉴权功能
        ).addPathPatterns("/**");
    }
}
