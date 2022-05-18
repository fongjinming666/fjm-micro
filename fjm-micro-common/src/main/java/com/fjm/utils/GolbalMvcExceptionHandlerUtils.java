package com.fjm.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.fjm.constant.MessageConstant;
import com.fjm.emun.ResultCode;
import com.fjm.exception.BadRequestException;
import com.fjm.ineterface.CommonResult;
import com.fjm.mapper.CustomMapper;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-05-02 下午4:42
 * @Description:
 */
@Slf4j
public class GolbalMvcExceptionHandlerUtils {

    /**
     * 通用错误处理 httpServlet
     *
     * @param response
     * @param exception
     * @param customMapper
     * @throws Exception
     */
    public static void writeFailedToResponse(HttpServletResponse response, Exception exception, CustomMapper customMapper) throws Exception {
        try {
            if (exception instanceof FeignException && ((FeignException) exception).responseBody().isPresent()) {
                ByteBuffer responseBody = ((FeignException) exception).responseBody().get();
                String bodyText = StandardCharsets.UTF_8.newDecoder().decode(responseBody.asReadOnlyBuffer()).toString();
                JSONObject jsonObject = customMapper.readValue(bodyText, JSONObject.class);
                Long resultCode = Optional.ofNullable(jsonObject.getLongValue("resultCode")).orElse(ResultCode.SERVER_ERROR.getCode());
                String message = Optional.ofNullable(jsonObject.getString("detailMessage")).orElse(MessageConstant.SERVER_ERROR_MSG);
                writeFailedToResponse(response, ResultCode.getResultCode(resultCode), message);
            } else if (exception instanceof HystrixRuntimeException) {
                BadRequestException badRequestException = (BadRequestException) (exception).getCause();
                writeFailedToResponse(response, badRequestException.getResult(), badRequestException.getMessage());
            } else if (exception instanceof BadRequestException) {
                writeFailedToResponse(response, ((BadRequestException) exception).getResult(), exception.getMessage());
            } else {
                writeFailedToResponse(response, ResultCode.SERVER_ERROR, ResultCode.SERVER_ERROR.getMessage());
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
            writeFailedToResponse(response, ResultCode.SERVER_ERROR, ResultCode.SERVER_ERROR.getMessage());
        }
        writeFailedToResponse(response, ResultCode.SERVER_ERROR, ResultCode.SERVER_ERROR.getMessage());
    }

    public static void writeFailedToResponse(HttpServletResponse response, ResultCode resultCode, String message) throws Exception {
        response.setStatus(HttpStatus.OK.value());
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String body = JSONUtil.toJsonStr(CommonResult.failed(resultCode, message));
        response.reset();
        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
