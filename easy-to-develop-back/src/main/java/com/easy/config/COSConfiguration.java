package com.easy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class COSConfiguration {
    @Value("${tencent cos.bucket}")
    public static String BUCKET_NAME;

    @Value("${tencent cos.region}")
    public static String REGION;

    @Value("${tencent cos.secretId}")
    public static String SECRET_ID;


    @Value("${tencent cos.secretKey}")
    public static String SECRET_KEY;
}
