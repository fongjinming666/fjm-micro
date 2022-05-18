package com.fjm.utils.validate.impl;


import com.fjm.constant.Constants;
import com.fjm.emun.ResultCode;
import com.fjm.exception.BadRequestException;
import com.fjm.utils.validate.ValidateCodeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.ServletWebRequest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-25 下午4:05
 * @Description: redis验证码操作
 */
public class ValidateCodeRepositoryImpl implements ValidateCodeRepository {

    private RedisTemplate<String, String> redisTemplate;

    public ValidateCodeRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(ServletWebRequest request, String code, String type) {
        redisTemplate.opsForValue().set(buildKey(request, type), code,
                //  有效期可以从配置文件中读取或者请求中读取
                Duration.ofMinutes(Constants.RANDOMCODE_MINUTES).getSeconds(), TimeUnit.SECONDS);
    }

    @Override
    public String get(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? "" : value.toString();
    }

    @Override
    public String get(ServletWebRequest request, String type) {
        Object value = redisTemplate.opsForValue().get(buildKey(request, type));
        return value == null ? "" : value.toString();
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void remove(ServletWebRequest request, String type) {
        redisTemplate.delete(buildKey(request, type));
    }

    /**
     * 构建 redis 存储时的 key
     *
     * @param request 请求
     * @param type    类型
     * @return key
     */
    public static String buildKey(ServletWebRequest request, String type) {
        String deviceId = request.getParameter(type);
        if (StringUtils.isBlank(deviceId)) {
            throw new BadRequestException(ResultCode.VALIDATE_FAILED, "请求中不存在 " + type);
        }
        if ("sms".equalsIgnoreCase(type)) {
            /** 处理短信登录的手机号. */
            String smsCode = Constants.syncSmsCode(request.getParameter("smsCode"));
            deviceId = smsCode + deviceId;
        }
        return "code:" + type + ":" + deviceId;
    }
}
