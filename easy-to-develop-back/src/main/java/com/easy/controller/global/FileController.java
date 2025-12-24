package com.easy.controller.global;

import com.easy.annotation.GlobalRestController;
import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.controller.BaseController;
import com.easy.entity.File;
import com.easy.entity.dto.FileDTO;
import com.easy.entity.dto.pg.FilePageQueryDTO;
import com.easy.entity.vo.FileVO;
import com.easy.service.impl.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@GlobalRestController
@RequestMapping("/files")
public class FileController extends BaseController<File, FileDTO, FileVO, FilePageQueryDTO> {

    FileServiceImpl service;

    @Autowired
    public FileController(FileServiceImpl service) {
        super("GlobalFileController", service);
        this.service = service;
    }

    @Override
    public Result<String> post(FileDTO data) throws AppException {
        return Result.success();
    }

    @PostMapping("/getUploadUrl")
    public Result<FileVO> getUploadUrl(@RequestBody FileDTO data) throws AppException {
        log.info("上传文件,data ---- {}", data);
        return Result.success(service.upload(data));
    }

    @PostMapping("/callback")
    public Result<String> callback(@RequestBody FileDTO data) throws AppException {
        log.info("上传文件回调,data ---- {}", data);
        service.callback(data);
        return Result.success();
    }
}
