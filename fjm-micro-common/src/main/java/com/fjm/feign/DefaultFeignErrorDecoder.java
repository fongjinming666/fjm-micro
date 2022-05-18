package com.fjm.feign;

import com.alibaba.fastjson.JSONObject;
import com.fjm.constant.MessageConstant;
import com.fjm.emun.ResultCode;
import com.fjm.exception.BadRequestException;
import com.fjm.mapper.CustomMapper;
import com.fjm.utils.ThrowableUtil;
import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-04-25 下午4:32
 * @Description: fegin全局异常拦截 fegin内部调用调用方输入处理
 */
@Slf4j
public class DefaultFeignErrorDecoder extends ErrorDecoder.Default {

    @Resource
    private CustomMapper customMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = super.decode(methodKey, response);
        if (exception instanceof RetryableException) {
            return exception;
        }
        /** 这里直接拿到我们抛出的异常信息. */
        exception = feginExceptionHandle(exception);
        return exception;
    }

    /**
     * 统一处理fegin异常
     *
     * @param exception
     * @return
     */
    public Exception feginExceptionHandle(Exception exception) {
        try {
            /** 正常业务返回数据. */
            if (exception instanceof FeignException && ((FeignException) exception).responseBody().isPresent()) {
                ByteBuffer responseBody = ((FeignException) exception).responseBody().get();
                String bodyText = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();
                JSONObject jsonObject = customMapper.readValue(bodyText, JSONObject.class);
                Long resultCode = Optional.ofNullable(jsonObject.getLongValue("resultCode")).orElse(ResultCode.SERVER_ERROR.getCode());
                String message = Optional.ofNullable(jsonObject.getString("detailMessage")).orElse(MessageConstant.SERVER_ERROR_MSG);
                return new BadRequestException(ResultCode.getResultCode(resultCode), message);
            } else {
                /** 出现除正常业务外的异常,需追踪. */
                log.error("DefaultFeignErrorDecoder远程调用服务,出现异常normal else:{}", ThrowableUtil.getStackTrace(exception));
                exception = new BadRequestException(ResultCode.SERVER_ERROR, MessageConstant.SERVER_ERROR_MSG + ",需追踪");
            }
        } catch (Exception ex) {
            /** 非常规逻辑异常. */
            log.error("DefaultFeignErrorDecoder非常规逻辑异常,出现特殊异常expecial:{}", ThrowableUtil.getStackTrace(ex));
            return new BadRequestException(ResultCode.SERVER_ERROR, MessageConstant.SERVER_ERROR_MSG);
        }
        return exception;
    }
}
