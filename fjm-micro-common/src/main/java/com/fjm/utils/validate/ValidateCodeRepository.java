package com.fjm.utils.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-25 下午4:05
 * @Description: 验证码资源处理
 */
public interface ValidateCodeRepository {

    /**
     * 保存
     *
     * @param request 请求
     * @param code    验证码
     * @param type    类型
     */
    void save(ServletWebRequest request, String code, String type);

    /**
     * 移除
     *
     * @param key 数据值
     */
    /**
     * 获取
     *
     * @param key 数据值
     * @return 验证码
     */
    String get(String key);

    /**
     * 获取
     *
     * @param request 请求
     * @param type    类型
     * @return 验证码
     */
    String get(ServletWebRequest request, String type);

    /**
     * 移除
     *
     * @param key 数据值
     */
    void remove(String key);


    /**
     * 移除
     *
     * @param request 请求
     * @param type    类型
     */
    void remove(ServletWebRequest request, String type);
}
