package com.fjm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-12-15 09:04
 * @Description:
 */
@Data
public class RequestClientVo {

    @ApiModelProperty(value = "客户端Id")
    private String clientId;

    @ApiModelProperty(value = "客户端转发地址")
    private String redirectUri;

    @ApiModelProperty(value = "区号")
    private String smsCode;

    @ApiModelProperty(value = "手机号")
    private String phone;
}
