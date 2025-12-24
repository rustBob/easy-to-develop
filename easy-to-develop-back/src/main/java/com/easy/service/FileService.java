package com.easy.service;

import com.easy.common.exception.AppException;
import com.easy.entity.dto.FileDTO;
import com.easy.entity.vo.FileVO;

public interface FileService {
    /**
     * 上传文件
     * @param fileDTO 文件信息
     * @return 文件上传路径
     */
    FileVO upload(FileDTO fileDTO) throws AppException;

    /**
     * 回调文件上传结果
     * @param dto 文件dto
     */
    void callback(FileDTO dto) throws AppException;
}
