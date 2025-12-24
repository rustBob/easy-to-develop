package com.easy.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.easy.config.COSConfiguration;
import com.easy.entity.File;
import com.easy.entity.dto.FileDTO;
import com.easy.entity.dto.pg.FilePageQueryDTO;
import com.easy.entity.vo.FileVO;
import com.easy.mapper.FileMapper;
import com.easy.service.FileService;
import com.easy.util.SnowflakeDistributeIdUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import com.tencent.cloud.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.TreeMap;

@Slf4j
@Service
public class FileServiceImpl extends BaseServiceImpl<File, FileDTO, FileVO, FilePageQueryDTO> implements FileService {

    @Autowired
    private SnowflakeDistributeIdUtil snowflakeDistributeIdUtil;

    @Autowired
    public FileServiceImpl(FileMapper mapper) {
        super(mapper);
    }

    /**
     * 上传文件
     *
     * @param data 文件数据
     * @return 上传的预签名地址
     */
    public FileVO upload(FileDTO data) {
        String userId = (String) StpUtil.getLoginId();

        File f = new File();
        BeanUtils.copyProperties(data, f);
        f.setUserId(Long.valueOf(userId));
        f.setId(snowflakeDistributeIdUtil.nextId());
        URL uploadUrl = getUploadUrl(data);

        save(f);

        return FileVO.builder()
                .id(f.getId())
                .uploadUrl(String.valueOf(uploadUrl))
                .build();
    }

    public void callback(FileDTO dto) {
        File f = new File();
        f.setStatus(1);
        BeanUtils.copyProperties(dto, f);

        updateById(f);
    }

    public URL getUploadUrl(FileDTO fileDTO){
        String userId = (String) StpUtil.getLoginId();
        Date expirationDate = new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        HttpMethodName method = HttpMethodName.PUT;

        COSClient tempClient = getTempClient();
        return tempClient.generatePresignedUrl(COSConfiguration.BUCKET_NAME,
                userId + '/' + fileDTO.getName() + '.' + fileDTO.getExtType(),
                expirationDate, method);
    }

    private COSClient getTempClient(){
        try {
            // 1 初始化用户身份信息（secretId, secretKey）。
            // SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
            String secretId = COSConfiguration.SECRET_ID;//用户的 SecretId，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            String secretKey = COSConfiguration.SECRET_KEY;//用户的 SecretKey，建议使用子账号密钥，授权遵循最小权限指引，降低使用风险。子账号密钥获取可参见 https://cloud.tencent.com/document/product/598/37140
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(COSConfiguration.REGION);
            ClientConfig clientConfig = new ClientConfig(region);
            // 这里建议设置使用 https 协议
            // 从 5.6.54 版本开始，默认使用了 https
            clientConfig.setHttpProtocol(HttpProtocol.https);
            // 3 生成 cos 客户端。
            return new COSClient(cred, clientConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("no valid secret !");
        }
    }
}
