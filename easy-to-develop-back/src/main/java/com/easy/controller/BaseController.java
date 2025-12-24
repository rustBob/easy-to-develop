package com.easy.controller;

import com.easy.common.Result;
import com.easy.common.exception.AppException;
import com.easy.service.BaseService;
import com.mybatisflex.core.paginate.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
public class BaseController<T, DTO, VO, PQDTO> {

    private final String controllerName;

    protected final BaseService<T, DTO, VO, PQDTO> service;

    public BaseController(String controllerName, BaseService<T, DTO, VO, PQDTO> service){
        this.controllerName = controllerName;
        this.service = service;
    }

    @GetMapping
    public Result<List<VO>> get(DTO data) throws AppException {
        log.info("{}:执行get方法，查询条件 ---- data：{}", controllerName, data);
        return Result.success(service.get(data));
    }

    @PostMapping
    public Result<String> post(@RequestBody DTO data) throws AppException {
        log.info("{}:执行post方法 ---- dto：{}", controllerName, data);
        service.post(data);
        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(Long id) throws AppException {
        log.info("{}:执行delete方法，id ---- {}", controllerName, id);
        service.delete(id);
        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody DTO data) throws AppException {
        log.info("{}:执行update方法 ---- dto：{}", controllerName, data);
        service.update(data);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Page<VO>> list(PQDTO pageQuery) throws AppException {
        log.info("{}:执行list方法，分页查询条件 ---- pageQuery：{}", controllerName, pageQuery);
        return Result.success(service.list(pageQuery));
    }
}
