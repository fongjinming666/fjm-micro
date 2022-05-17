package com.fjm.emun;


import com.fjm.ineterface.IErrorCode;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-18 下午3:49
 * @Description:
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作结果--成功
     */
    SUCCESS(200, "成功"),
    /**
     * 请求参数有误
     */
    VALIDATE_FAILED(400, "请求参数有误"),
    /**
     * 登录凭证无效
     */
    UNAUTHORIZED(401, "登录凭证无效，请重新登录!"),
    /**
     * 没有访问资源权限
     */
    FORBIDDEN(403, "没有访问资源权限,服务器拒绝请求"),
    /**
     * 数据不存在
     */
    NOT_FOUND(404, "数据不存在,请联系管理员!"),
    /**
     * 账户已被禁用
     */
    ACCOUNT_DISABLED(430, "账户已被禁用,请联系管理员!"),
    /**
     * 账号已被锁定
     */
    ACCOUNT_LOCKED(431, "账号已被锁定，请联系管理员!"),
    /**
     * 账号已过期
     */
    ACCOUNT_EXPIRED(432, "账号已过期，请联系管理员!"),
    /**
     * 没有访问权限
     */
    PERMISSION_DENIED(433, "没有访问权限，请联系管理员!"),
    /**
     * 该账户的登录凭证已过期
     */
    CREDENTIALS_EXPIRED(434, "该账户的登录凭证已过期，请重新登录!"),
    /**
     * 微信授权失败
     */
    WECHAT_AUTH_FAILED(450, "微信授权失败"),
    /**
     * 苹果授权失败
     */
    APPLE_AUTH_FAILED(451, "苹果授权失败"),
    /**
     * 一键登录失败
     */
    ONEKEY_LOGIN_FAILED(452, "一键登录失败"),
    /**
     * 服务器错误
     */
    SERVER_ERROR(500, "服务器错误"),

    /**
     * 不存在
     */
    NOT_EXIST(1000, "不存在"),

    CALL_REMOTE_API_ERROR(2000, "服务端调用API失败"),

    /**
     * 服务器错误
     */
    ERROR(5000, "服务器错误")
    /** -----------------------------兼容魔系的代码----------------------------- */
    ;


    private long code;
    private String message;

    ResultCode(long code) {
        this.code = code;
    }

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public static ResultCode getResultCode(long code) {
        ResultCode[] resultCodeEnums = values();
        for (ResultCode codeEnum : resultCodeEnums) {
            if (codeEnum.getCode() == code) {
                return codeEnum;
            }
        }
        return null;
    }
}
