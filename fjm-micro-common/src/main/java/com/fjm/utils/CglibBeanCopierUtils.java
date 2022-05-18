package com.fjm.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jinmingfong
 * @CreateTime: 2020-04-15 23:39
 * @Description: 将BeanCopier做成静态类，方便拷贝
 */
@Slf4j
public class CglibBeanCopierUtils {
    /**
     * 保存对应的beanCopier
     */
    public static final Map<String, BeanCopier> beanCopierMap = new HashMap<>();

    /**
     * 属性转换
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        String beanKey = generateKey(source.getClass(), target.getClass());
        BeanCopier copier = null;
        if (!beanCopierMap.containsKey(beanKey)) {
            copier = BeanCopier.create(source.getClass(), target.getClass(), false);
            beanCopierMap.put(beanKey, copier);
        } else {
            copier = beanCopierMap.get(beanKey);
        }
        copier.copy(source, target, null);
    }

    private static String generateKey(Class<?> class1, Class<?> class2) {
        return class1.toString() + class2.toString();
    }

}
