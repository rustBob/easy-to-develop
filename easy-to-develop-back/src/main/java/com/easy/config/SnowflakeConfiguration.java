package com.easy.config;

import com.easy.util.SnowflakeDistributeIdUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfiguration {

    @Bean
    public static SnowflakeDistributeIdUtil getSnowflakeUtil(){
        // 先配置为0 有多机器再做配置
        return new SnowflakeDistributeIdUtil(0, 0);
    }
}
