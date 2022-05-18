package com.fjm.exception;

import com.fjm.emun.ResultCode;
import lombok.Getter;

/**
 * @Author: jinmingfong
 * @CreateTime: 2020-04-15 23:39
 * @Description:
 */
@Getter
public class BadRequestException extends RuntimeException {

    public ResultCode result;

    public BadRequestException(ResultCode result) {
        super(result.getMessage());
        this.result = result;
    }

    public BadRequestException(ResultCode result, String message) {
        super(message);
        this.result = result;
    }
}

