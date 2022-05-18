package com.fjm.feign;

import com.fjm.constant.AuthConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-07-14 下午4:46
 * @Description:
 */
@Slf4j
public class DefaultAddHeaderRequestInterceptor implements RequestInterceptor {

    /**
     * 增加自动传递请求头信息
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        //log.info("init to sync feign function headers(增加自动传递请求头信息)!");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (request != null) {
                Enumeration<String> headerNames = request.getHeaderNames();
                if (headerNames != null) {
                    while (headerNames.hasMoreElements()) {
                        String name = headerNames.nextElement();
                        String values = request.getHeader(name);
                        if (name.equalsIgnoreCase(AuthConstant.JWT_TOKEN_HEADER) || name.equalsIgnoreCase(AuthConstant.USER_TOKEN_HEADER)) {
                            /** 过滤重要请求头信息. */
                            template.header(name, values);
                        }
                    }

                }

                /** parameters. */
                Enumeration<String> bodyNames = request.getParameterNames();
                StringBuffer body = new StringBuffer();
                /*if (bodyNames != null) {
                    while (bodyNames.hasMoreElements()) {
                        String name = bodyNames.nextElement();
                        String values = request.getParameter(name);
                        if (name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_ID)
                                || name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_SECRET)
                                || name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_GRANTTYPE)
                                || name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_SCOPE)
                                || name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_PASSWORD)
                                || name.equalsIgnoreCase(AuthConstant.JWT_USER_NAME_KEY)
                                || name.equalsIgnoreCase(AuthConstant.PARAMETERS_CLIENT_GRANTTYPE)) {
                            *//** 过滤重要parameters信息. *//*
                            body.append(name).append("=").append(values).append("&");
                        }
                    }
                    if (body.length() != 0) {
                        body.deleteCharAt(body.length() - 1);
                        template.body(body.toString());
                        log.info("feign interceptor body(增加自动传递请求body信息):{}", body.toString());
                    }
                }*/
                log.info("feign interceptor headers(增加自动传递请求头信息):{}", template);
            }
        }
    }
}
