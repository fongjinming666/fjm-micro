package com.fjm.feign;

import com.fjm.constant.MessageConstant;
import com.fjm.emun.ResultCode;
import com.fjm.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 2
 * * @Author: jinmingfong
 * * @CreateTime: 021-04-25 下午4:32
 *
 * @Description: fegin全局异常数据处理 fegin内部调用被调用方输出处理
 */
@Slf4j
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        Throwable error = this.getError(webRequest);
        if (error instanceof BadRequestException) {
            errorAttributes.put("resultCode", ((BadRequestException) error).getResult().getCode());
            errorAttributes.put("detailMessage", StringUtils.isNotBlank(error.getMessage()) ? error.getMessage() : ((BadRequestException) error).getResult().getMessage());
        } else {
            errorAttributes.put("resultCode", ResultCode.SERVER_ERROR.getCode());
            errorAttributes.put("detailMessage", MessageConstant.SERVER_ERROR_MSG);
            log.error("CustomErrorAttributes远程调用服务，出现异常normal else:", error);
            if (error != null && error.getCause() != null) {
                if (error.getCause() instanceof BadRequestException) {
                    errorAttributes.put("resultCode", ((BadRequestException) error.getCause()).getResult().getCode());
                    errorAttributes.put("detailMessage", error.getMessage());
                }
            }
        }
        return errorAttributes;
    }
}
