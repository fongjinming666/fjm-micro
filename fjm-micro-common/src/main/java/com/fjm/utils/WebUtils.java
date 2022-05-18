package com.fjm.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fjm.emun.ResultCode;
import com.fjm.ineterface.CommonResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-04-26 下午5:41
 * @Description: 通用输出错误
 */
public class WebUtils {

    public static Mono writeFailedToResponse(ServerHttpResponse response, ResultCode resultCode, String message) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = JSONUtil.toJsonStr(CommonResult.failed(resultCode, message));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

    public static Mono writeFailedHilinkSmartResponse(ServerHttpResponse response, long errorCode, String message) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        JSONObject jsonObject = new JSONObject();
        JSONObject payload = new JSONObject();
        JSONObject header = new JSONObject();
        header.putOnce("messageId", UUID.randomUUID().toString());
        jsonObject.putOnce("header", header);

        payload.putOnce("code", errorCode);
        payload.putOnce("description", message);
        jsonObject.putOnce("payload", payload);

        String body = JSONUtil.toJsonStr(jsonObject);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }
}
