package com.easy.service.impl;

import com.easy.common.Status;
import com.easy.common.constant.ClassPackageNameAndSuffixConst;
import com.easy.common.constant.ClassPropertyAndMethodConst;
import com.easy.common.exception.AppException;
import com.easy.service.BaseService;
import com.easy.util.AssembleParamsUtil;
import com.mybatisflex.core.BaseMapper;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Getter
@NoArgsConstructor
public class BaseServiceImpl<T, DTO, VO, PQDTO> implements BaseService<T, DTO, VO, PQDTO>{

    protected BaseMapper<T> mapper;

    public BaseServiceImpl(BaseMapper<T> mapper){
        this.mapper = mapper;
    }

    /**
     * get方法
     * @param dto 查询参数
     * @return VO
     * @throws AppException 自定义应用异常
     */
    @Override
    public List<VO> get(DTO dto) throws AppException {
        beforeGet(dto);
        List<T> e = doGet(dto);
        return afterGet(e);
    }

    /**
     * 执行get前执行的方法
     * @param dto 查询参数
     * @throws AppException 自定义应用异常
     */
    protected void beforeGet(DTO dto) throws AppException {
    }

    /**
     * 实际get执行逻辑
     * @param dto 查询参数
     * @return T
     * @throws AppException 自定义应用异常
     */
    protected List<T> doGet(DTO dto) throws AppException {
        if(null == dto){
            log.error("数据为空");
            throw new AppException(Status.DATA_IS_NULL);
        }

        QueryWrapper queryWrapper;
        try {
            queryWrapper = AssembleParamsUtil.generateQueryWrapperByDTOClass(dto, dto.getClass());
        } catch (Exception e) {
            log.error("参数转换异常 ---- {}", e.getMessage());
            throw new AppException(Status.PARAMETER_CONVERSION_FAILED);
        }

        return mapper.selectListWithRelationsByQuery(queryWrapper);
    }

    /**
     * 执行get后执行的方法
     * @param entities 实体集合
     * @throws AppException 自定义应用异常
     */
    protected List<VO> afterGet(List<T> entities) throws AppException{
        return convertToVO(entities);
    }

    /**
     * post方法
     * @param dto 添加的数据
     * @return VO
     * @throws AppException 自定义应用异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void post(DTO dto) throws AppException {
        beforePost(dto);
        T e = doPost(dto);
        afterPost(e, dto);
    }

    /**
     * 执行post前执行的方法
     * @param dto 添加的数据
     * @throws AppException 自定义应用异常
     */
    protected void beforePost(DTO dto) throws AppException{
    }

    /**
     * post方法的具体逻辑
     * @param dto 添加的数据
     * @throws AppException 自定义应用异常
     */
    protected T doPost(DTO dto) throws AppException {
        log.info("添加数据 ---- data：{}", dto);

        if(null == dto){
            log.error("数据为空");
            throw new AppException(Status.DATA_IS_NULL);
        }

        T e = convertToEntity(dto);

        boolean save = save(e);
        if(!save){
            log.error("添加失败 ---- data：{}", dto);
            throw new AppException(Status.FAILED_TO_ADD);
        }

        return e;
    }

    /**
     * 执行post后执行的方法
     * @param e 添加后的数据
     * @param dto 添加前的数据
     * @throws AppException 自定义应用异常
     */
    protected void afterPost(T e, DTO dto) throws AppException{
    }

    /**
     * delete方法
     * @param id 要删除的id
     * @return VO
     * @throws AppException 自定义应用异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) throws AppException {
        beforeDelete(id);
        T e = doDelete(id);
        afterDelete(e);
    }

    /**
     * 执行delete前执行的方法
     * @param id 要删除的id
     * @throws AppException 自定义应用异常
     */
    protected void beforeDelete(Long id) throws AppException{
    }

    /**
     * delete方法的具体逻辑
     * @param id 要删除的id
     * @return T
     * @throws AppException 自定义应用异常
     */
    protected T doDelete(Long id) throws AppException{
        if(null == id){
            log.error("id为空");
            throw new AppException(Status.ID_IS_NULL);
        }

        T e = mapper.selectOneWithRelationsById(id);
        if(null == e){
            log.error("数据不存在 ---- id：{}", id);
            throw new AppException(Status.NOT_EXISTS);
        }

        boolean b = removeById(id);
        if(!b) {
            log.error("删除失败 ---- id：{}", id);
            throw new AppException(Status.FAILED_TO_DELETE);
        }
        return e;
    }

    /**
     * 执行delete后执行的方法
     * @param e 删除的数据
     * @throws AppException 自定义应用异常
     */
    protected void afterDelete(T e) throws AppException{
    }

    /**
     * update方法
     * @param dto 修改的数据
     * @throws AppException 自定义应用异常
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DTO dto) throws AppException {
        beforeUpdate(dto);
        doUpdate(dto);
        afterUpdate(dto);
    }

    /**
     * 分页查询
     * @param query 分页查询参数
     * @return Page<VO> 分页对象
     * @throws AppException 自定义应用异常
     */
    @Override
    public Page<VO> list(PQDTO query) throws AppException {
        beforeList(query);
        Page<T> page = doList(query);
        return afterList(page);
    }

    /**
     * 执行list前执行方法
     * @param query 分页查询参数
     * @throws AppException 自定义应用异常
     */
    protected void beforeList(PQDTO query) throws AppException{
    }

    /**
     * list方法具体逻辑
     * @param query 分页查询参数
     * @return Page<T>
     */
    protected Page<T> doList(PQDTO query) throws AppException {
        Map<String, Object> pageQueries;
        try {
            pageQueries = AssembleParamsUtil.assembleParamsFromDTOClass(query, query.getClass());
        } catch (Exception e) {
            log.error("参数转换异常 ---- {}", e.getMessage());
            throw new AppException(Status.PARAMETER_CONVERSION_FAILED);
        }

        Page<T> page = new Page<>((Number) pageQueries.get(ClassPropertyAndMethodConst.PAGE_NUM),
                (Number) pageQueries.get(ClassPropertyAndMethodConst.PAGE_SIZE));

        QueryWrapper queryWrapper;
        try {
            queryWrapper = AssembleParamsUtil.generateQueryWrapperByDTOClass(query, query.getClass());
        } catch (Exception e) {
            log.error("参数转换异常 ---- {}", e.getMessage());
            throw new AppException(Status.PARAMETER_CONVERSION_FAILED);
        }

        return mapper.paginateWithRelations(page, queryWrapper);
    }

    /**
     * 执行list方法后执行方法
     * @param page 分页对象
     */
    protected Page<VO> afterList(Page<T> page) throws AppException {
        List<VO> vos = convertToVO(page.getRecords());
        Page<VO> vosPage = new Page<>(page.getPageNumber(), page.getPageSize(), page.getTotalRow());
        vosPage.setRecords(vos);
        return vosPage;
    }

    /**
     * 执行update方法前执行的方法
     * @param dto 修改的数据
     * @throws AppException 自定义应用异常
     */
    protected void beforeUpdate(DTO dto) throws AppException{
    }

    /**
     * update方法的具体逻辑
     * @param dto 修改的数据
     * @throws AppException 自定义应用异常
     */
    protected void doUpdate(DTO dto) throws AppException {
        if(null == dto){
            log.error("数据为空");
            throw new AppException(Status.DATA_IS_NULL);
        }

        boolean b = updateById(convertToEntity(dto));
        if(!b) {
            log.error("更新失败 ---- data：{}", dto);
            throw new AppException(Status.FAILED_TO_UPDATE);
        }
    }

    /**
     * 执行update后执行的方法
     * @param dto 修改的数据
     * @throws AppException 自定义应用异常
     */
    protected void afterUpdate(DTO dto) throws AppException{
    }

    /**
     * 将实体类转换为VO
     * @param entity 实体类
     * @return VO
     */
    @Override
    public VO convertToVO(T entity) throws AppException {
        String eName = entity.getClass().getSimpleName();
        Class<?> voClass;

        try {
            voClass = Class.forName(ClassPackageNameAndSuffixConst.VO.getPackageName()
                    + "." + eName + ClassPackageNameAndSuffixConst.VO.getSuffix());
        } catch (ClassNotFoundException e) {
            log.error("类未找到 ---- entity：{}", entity);
            throw new AppException(Status.CLASS_NOT_FOUND);
        }

        VO vo;
        try {
            vo = (VO) voClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("获取实体类对象失败 ---- {}", e.getMessage());
            throw new AppException(Status.FAILED_TO_GET_OBJECT);
        }

        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    /**
     * 将实体类集合转换为VO集合
     * @param entities 实体类集合
     * @return List<VO>
     */
    public List<VO> convertToVO(List<T> entities) throws AppException {
        List<VO> vos = new ArrayList<>();
        for(T e: entities){
            vos.add(convertToVO(e));
        }
        return vos;
    }

    /**
     * 将DTO转换为实体类
     * @param dto DTO
     * @return T
     */
    @Override
    public T convertToEntity(DTO dto) throws AppException {
        String dtoName = dto.getClass().getSimpleName();

        Class<?> eClass;
        try {
            eClass = Class.forName(ClassPackageNameAndSuffixConst.ENTITY.getPackageName()
                    + "." + dtoName.substring(0, dtoName.length() - 3));
        } catch (ClassNotFoundException e) {
            log.error("类未找到 ---- dto：{}", dto);
            throw new AppException(Status.CLASS_NOT_FOUND);
        }

        T e;
        try {
            e = (T)eClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            log.error("获取实体类对象失败 ---- {}", ex.getMessage());
            throw new AppException(Status.FAILED_TO_GET_OBJECT);
        }

        BeanUtils.copyProperties(dto, e);
        return e;
    }
}
