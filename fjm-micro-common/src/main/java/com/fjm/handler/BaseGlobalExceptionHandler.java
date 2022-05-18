package com.fjm.handler;

import com.fjm.constant.MessageConstant;
import com.fjm.emun.ResultCode;
import com.fjm.exception.BadRequestException;
import com.fjm.ineterface.CommonResult;
import com.fjm.utils.ThrowableUtil;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * controller全局错误拦截器
 *
 * @Author: jinmingfong
 * @CreateTime: 2021-03-26 下午3:17
 * @Description:
 */
@Slf4j
public class BaseGlobalExceptionHandler {

    /**
     * 处理所自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public CommonResult handleException(BadRequestException e) {
        /** 打印堆栈信息. */
        return CommonResult.failed(e.getResult(), e.getMessage());
    }

    /**
     * 处理远程内部调用的exception
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(HystrixRuntimeException.class)
    public CommonResult handleFeginException(Throwable exception) {
        try {
            /** 正常业务返回数据. */
            Throwable ex = exception.getCause();
            if (ex instanceof BadRequestException) {
                return CommonResult.failed(((BadRequestException) ex).getResult(), ex.getMessage());
            } else {
                /** 出现除正常业务外的异常,需追踪. */
                log.error("DefaultFeignErrorDecoder远程调用服务,出现异常normal else:{}", ThrowableUtil.getStackTrace(ex));
                return CommonResult.failed(ResultCode.SERVER_ERROR, MessageConstant.SERVER_ERROR_MSG + ",需追踪");
            }
        } catch (Exception ex) {
            /** 非常规逻辑异常. */
            log.error("DefaultFeignErrorDecoder非常规逻辑异常,出现特殊异常expecial:{}", ThrowableUtil.getStackTrace(ex));
            return CommonResult.failed(ResultCode.SERVER_ERROR, MessageConstant.SERVER_ERROR_MSG);
        }
    }


    /**
     * 用来处理bean validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public <T> CommonResult<T> resolveConstraintViolationException(ConstraintViolationException ex) {
        String msg;
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(constraintViolations)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                msgBuilder.append(constraintViolation.getMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            msg = errorMessage;
        } else {
            msg = ex.getMessage();
        }
        log.info("bean参数检验不通过:{}", msg);
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, msg);
    }

    /**
     * 用来处理method validation异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> CommonResult<T> resolveMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
        String msg;
        if (!CollectionUtils.isEmpty(objectErrors)) {
            StringBuilder msgBuilder = new StringBuilder();
            for (ObjectError objectError : objectErrors) {
                msgBuilder.append(objectError.getDefaultMessage()).append(",");
            }
            String errorMessage = msgBuilder.toString();
            if (errorMessage.length() > 1) {
                errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
            }
            msg = errorMessage;
        } else {
            msg = ex.getMessage();
        }
        log.info("method参数检验不通过:{}", msg);
        return CommonResult.failed(ResultCode.VALIDATE_FAILED, msg);
    }

    /**
     * SQL异常
     */
    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public <T> CommonResult<T> handlerSQLException(SQLException ex) {
        log.error("SQL异常:result={}", ex.getMessage());
        return CommonResult.failed(ResultCode.SERVER_ERROR, ex.getMessage());
    }

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    public CommonResult handleException(Throwable e) {
        /** 打印堆栈信息. */
        log.error("Exception异常:result{}", ThrowableUtil.getStackTrace(e));
        return CommonResult.failed(ResultCode.ERROR, ThrowableUtil.getStackTrace(e));
    }
}
