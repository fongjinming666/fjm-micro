package com.fjm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-12-15 09:04
 * @Description:
 */
@Data
public class LoginPhonePasswordVo {

    /**
     * 区号
     */
    @Pattern(regexp = "^[0-9]*$", message = "区号格式有误")
    @ApiModelProperty(value = "区号")
    private String smsCode;

    /**
     * 手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 18, message = "请设置至少6位数的密码，包含数字与字母")
    @ApiModelProperty(value = "密码")
    private String password;
}
