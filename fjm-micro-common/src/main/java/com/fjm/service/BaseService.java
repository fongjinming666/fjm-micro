package com.fjm.service;


import java.util.List;

/**
 * 系统服务基础接口，定义最简单的增删改查功能。
 *
 * @param <T> 数据对象
 * @author liyang
 */
public interface BaseService<T> {
    /**
     * 添加
     *
     * @param t t
     */
    void save(T t);

    /**
     * 查询列表
     *
     * @param t t
     * @return List<T>
     */
    List<T> queryList(T t);

    /**
     * 根据ID查询
     *
     * @param id id
     * @return T
     */
    T queryById(String id);

    /**
     * 通过id更新
     *
     * @param t
     */
    void updateById(T t);

    /**
     * 根据ID删除
     *
     * @param id id
     */
    void deleteById(String id);


}
