package com.fjm.emun;

import lombok.Getter;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-18 下午3:49
 * @Description:
 */
@Getter
public enum GrantTypeEnum {

    IMPLICIT("implicit", "简化模式"),

    CLIENT_CREDENTIALS("client_credentials", "客户端模式"),

    REFRESH_TOKEN("refresh_token", "refresh模式"),

    AUTHORIZATION_CODE("authorization_code", "授权码模式"),

    PASSWORD("password", "密码模式"),

    SMS("password", "短信登录"),

    ONE_KEY("one_key", "一键登录"),

    WECHAT("wechat", "微信授权登录"),

    APPLE("apple", "苹果授权登录"),
    ;

    private String value;

    private String desc;

    GrantTypeEnum(String value, String desc) {
        this.desc = desc;
        this.value = value;
    }

    public static String getDesc(Integer value) {
        GrantTypeEnum[] deleteEnums = values();
        for (GrantTypeEnum deleteEnum : deleteEnums) {
            if (deleteEnum.getValue().equals(value)) {
                return deleteEnum.getDesc();
            }
        }
        return null;
    }
}
