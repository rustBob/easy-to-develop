package com.easy.util;

import com.easy.common.constant.ClassPropertyAndMethodConst;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于组装参数
 */
public class AssembleParamsUtil {

    /**
     * 根据DTO类组装参数
     * @param data dto数据
     * @param dtoClass dto类
     */
    public static Map<String, Object> assembleParamsFromDTOClass(Object data, Class<?> dtoClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> params = new HashMap<>();

        Field[] fields = dtoClass.getDeclaredFields();
        for(Field field : fields){
            String methodName = ClassPropertyAndMethodConst.GET_METHOD_PREFIX + StringUtils.capitalize(field.getName());
            Object param = dtoClass.getMethod(methodName).invoke(data);
            if(null == param) continue;
            params.put(field.getName(), param);
        }
        return params;
    }

    /**
     * 根据DTO类生成QueryWrapper
     * @param data dto数据
     * @param dtoClass dto类
     */
    public static QueryWrapper generateQueryWrapperByDTOClass(Object data, Class<?> dtoClass) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        Field[] fields = dtoClass.getDeclaredFields();
        QueryWrapper queryWrapper = new QueryWrapper();
        for(Field field : fields){
            String methodName = ClassPropertyAndMethodConst.GET_METHOD_PREFIX + StringUtils.capitalize(field.getName());
            Object param = dtoClass.getMethod(methodName).invoke(data);

            // 忽略分页参数
            if(field.getName().equals(ClassPropertyAndMethodConst.PAGE_NUM) ||
                    field.getName().equals(ClassPropertyAndMethodConst.PAGE_SIZE)) continue;

            queryWrapper = field.getName().contains("name") && param != null ?
                    queryWrapper.like(field.getName(), param + "%") :
                    queryWrapper.eq(field.getName(), param);
        }
        return queryWrapper;
    }
}
