package com.fjm.utils;

import com.fjm.context.ApplicationContextUtils;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.Map;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-05-28 上午9:38
 * @Description:
 */
public class OkHttpUtil {
    private static final ThreadLocal<String> jsonParamsTL = new ThreadLocal<>();

    private static final Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);
    public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";

    /**
     * 根据map获取get请求参数
     *
     * @param queries
     * @return
     */
    public static StringBuffer getQueryString(String url, Map<String, Object> queries) {
        StringBuffer sb = new StringBuffer(url);
        if (queries != null && queries.keySet().size() > 0) {
            boolean firstFlag = true;
            Iterator iterator = queries.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry<String, String>) iterator.next();
                if (firstFlag) {
                    sb.append("?" + entry.getKey() + "=" + entry.getValue());
                    firstFlag = false;
                } else {
                    sb.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
        }
        return sb;
    }

    /**`
     * 调用okhttp的newCall方法`
     *
     * @param request
     * @return
     */
    private static String execNewCall(Request request) {
        Response response = null;
        try {
            OkHttpClient okHttpClient = ApplicationContextUtils.getBean(OkHttpClient.class);
            response = okHttpClient.newCall(request).execute();
            ////异步请求
            //Call call = client.newCall(request);
            //call.enqueue(new Callback() {
            //    @Override
            //    public void onFailure(Call call, IOException e) {
            //        System.out.println("请求失败");
            //    }
            //
            //    @Override
            //    public void onResponse(Call call, Response response) throws IOException {
            //        System.out.println(response.body().string());
            //    }
            //});
//            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                logger.error("返回有误:{},{}", jsonParamsTL.get(), response.body().string());
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> jsonParams={}, ex = {},{}", jsonParamsTL.get(), request, e);
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }

    /**
     * 调用okhttp的newCall方法
     *
     * @param request
     * @return
     */
    private static String execRrpcCall(Request request) {
        Response response = null;
        try {
            OkHttpClient okHttpClient = ApplicationContextUtils.getBean(OkHttpClient.class);
            response = okHttpClient.newCall(request).execute();
            ////异步请求
            //Call call = client.newCall(request);
            //call.enqueue(new Callback() {
            //    @Override
            //    public void onFailure(Call call, IOException e) {
            //        System.out.println("请求失败");
            //    }
            //
            //    @Override
            //    public void onResponse(Call call, Response response) throws IOException {
            //        System.out.println(response.body().string());
            //    }
            //});
//            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                String msg = response.body().string();
                logger.error("返回有误:{},{}", jsonParamsTL.get(), msg);
                return msg;
            }
        } catch (Exception e) {
            logger.error("okhttp3 put error >> jsonParams={}, ex = {},{}", jsonParamsTL.get(), request, e);
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * get
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String get(String url, Map<String, Object> queries) {
        StringBuffer sb = getQueryString(url, queries);
        Request request = new Request.Builder()
                .addHeader("Connection", "close")
                .url(sb.toString())
                .build();
        return execNewCall(request);
    }

    /**
     * Get请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String getJsonParams(String url, Map<String, Object> queries, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = builder.url(url)
                .addHeader("Connection", "close")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Safari/537.36")
                .addHeader("Connection", "keep-alive")
                .get()
                .build();
        return execNewCall(request);
    }


    /**
     * post
     *
     * @param url    请求的url
     * @param jsonParams post form 提交的参数
     * @return
     */
    public static String postHiLink4CloudParams(String url, String jsonParams,String token) {
        FormBody.Builder builder = new FormBody.Builder();
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        //添加参数
        /*if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }*/
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization","Bearer "+token)
                .addHeader("Accept","application/json")
                .post(requestBody)
                .build();
        return execRrpcCall(request);
    }

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postFormParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();
        return execNewCall(request);
    }


    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Connection", "close")
                .post(requestBody)
                .build();
        return execNewCall(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    /*public static String postHiLinkParams(String url, String jsonParams) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        return execNewCall(request);
    }*/

    /**
     * post
     *
     * @param url    请求的url
     * @param params post form 提交的参数
     * @return
     */
    public static String postHiLinkParams(String url, Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        //添加参数
        if (!CollectionUtils.isEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(builder.build())
                .build();
        return execRrpcCall(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求header
     */
    public static String postJsonParams(String url, String jsonParams, Map<String, String> headerMap) {
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        Request.Builder builder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.addHeader("Connection", "close")
                .url(url)
                .post(requestBody);
        Request request = builder.build();
        return execNewCall(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postJsonParams(String url, String jsonParams, String token) {
        logger.info("jsonParams={}", jsonParams + url);
        jsonParamsTL.set(jsonParams + url);
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        Request request = new Request.Builder()
                .url(url).addHeader("Access-Token", token)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Safari/537.36")
                .addHeader("Connection", "keep-alive")
                .post(requestBody)
                .build();
        return execNewCall(request);
    }

    /**
     * Post请求发送JSON数据....{"name":"zhangsan","pwd":"123456"}
     * 参数一：请求Url
     * 参数二：请求的JSON
     * 参数三：请求回调
     */
    public static String postXlinkRrpcJsonParams(String url, String jsonParams, String token) {
        logger.info("jsonParams={}", jsonParams + url);
        jsonParamsTL.set(jsonParams + url);
        RequestBody requestBody = RequestBody.create(MediaType.parse(APPLICATION_JSON_UTF8_VALUE), jsonParams);
        Request request = new Request.Builder()
                .url(url).addHeader("Access-Token", token)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.162 Safari/537.36")
                .addHeader("Connection", "keep-alive")
                .post(requestBody)
                .build();
        return execRrpcCall(request);
    }

    public static String getJsonParams(String url, String token) {
        Request request = new Request.Builder()
                .url(url).addHeader("Content-Type", APPLICATION_JSON_UTF8_VALUE).addHeader("Access-Token", token)
                .addHeader("User-Agent", "PostmanRuntime/7.21.0")
                .addHeader("Connection", "keep-alive")
                .get()
                .build();
        return execNewCall(request);
    }

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     * 参数三：请求回调
     */
    public static String postXmlParams(String url, String xml) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        return execNewCall(request);
    }

    /**
     * get
     *
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public static String getAddHead(String url, Map<String, Object> queries, Map<String, String> headers) {
        StringBuffer sb = getQueryString(url, queries);
        Request.Builder builder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        builder.addHeader("Connection", "close")
                .url(sb.toString());

        Request request = builder.build();
        return execNewCall(request);
    }
}
