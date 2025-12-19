package com.easy.service;

import com.easy.common.exception.AppException;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

public interface BaseService<T, DTO, VO, PQDTO> extends IService<T>{
    /**
     * get 方法
     * @param data 查询参数
     * @return VO
     * @throws AppException 应用异常
     */
    List<VO> get(DTO data) throws AppException;

    /**
     * post 方法
     * @param data 新增参数
     * @throws AppException 应用异常
     */
    void post(DTO data) throws AppException;

    /**
     * delete 方法
     * @param id 删除的id
     * @throws AppException 应用异常
     */
    void delete(Long id) throws AppException;

    /**
     * update 方法
     * @param data 修改参数
     * @throws AppException 应用异常
     */
    void update(DTO data) throws AppException;

    /**
     * list 方法
     * @param pageQuery 分页查询参数
     * @return PageResult<VO>
     * @throws AppException 应用异常
     */
    Page<VO> list(PQDTO pageQuery) throws AppException;

    /**
     * 将实体类转化为VO
     * @param entity 实体类
     * @return VO
     */
    VO convertToVO(T entity) throws AppException;

    /**
     * 将 DTO 转化为实体类
     * @param data DTO
     * @return 实体类
     */
    T convertToEntity(DTO data) throws AppException;
}
