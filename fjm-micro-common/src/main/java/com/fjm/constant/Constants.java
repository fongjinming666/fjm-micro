package com.fjm.constant;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: jinmingfong
 * @CreateTime: 2021-03-18 下午3:55
 * @Description:
 */
public class Constants {

    public static String BEARER_AUTHENTICATION = "Bearer";
    public static String HEADER_AUTHENTICATION = "authorization";

    public static final String NUMBER_FILTER = "[^0-9]";

    public static final String phoneReg = "\"?0?(13|14|15|16|18|17|19)[0-9]{9}\"?";
    public static final String IS_NUMBER_REG = "^[0-9]*$";
    public static final String IS_EMAIL_REG = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";
    public static final String HK_PHONE_REG = "^(5|6|8|9)\\d{7}$";
    public static final String MO_PHONE_REG = "^6\\d{7}$";
    public static final String TW_PHONE_REG = "^09\\d{8}$";
    public static final String EMAIL_REG = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    public static final Integer DELETED_YES = 1; // 已删除
    public static final Integer DELETED_NO = 0;// 未删除

    // 排序
    public static final String SORT_DESC = "DESC";
    public static final String SORT_ASC = "ASC";

    public static final Integer ONLINE = 1;
    public static final Integer OFFLINE = 0;

    // 启用、禁用
    public static final Integer STATUS_ENABLE = 1;
    public static final Integer STATUS_DISABLED = 0;

    /**
     * 默认查询开始条数.
     */
    public static final int DEFAULT_START = 1;

    /**
     * 默认查询条数.
     */
    public static final int DEFAULT_SIZE = 10;

    /**
     * 获取数据失败(记录不存在).
     */
    public static final int GET_DATA_ERROR = -1;

    /**
     * 获取数据失败(执行查询失败).
     */
    public static final int GET_DATA_EMPTY = 0;

    /**
     * 验证码redis持续时间 10分钟.
     */
    public static final int RANDOMCODE_MINUTES = 10;

    /**
     * 默认区号
     */
    public static final String DEFAULT_SMS_CODE = "86";

    /**
     * 根据入参内容定位smsCode
     *
     * @param smsCode
     * @return
     */
    public static String syncSmsCode(String smsCode) {
        smsCode = StringUtils.isEmpty(smsCode) ? Constants.DEFAULT_SMS_CODE : smsCode;
        return smsCode;
    }

    /**
     * 国家简称/区号对应关系
     *
     * @return
     */
    public static Map<Integer, String> smsCodeMap() {
        HashMap map = new HashMap();
        map.put(86, "ZH");
        map.put(852, "Hk");
        map.put(853, "MO");
        map.put(886, "TW");
        return map;
    }

    /**
     * 根据区号获取区号对应国家code
     *
     * @param smsCode
     * @return
     */
    public static String queryCountryCode(String smsCode) {
        if (!StringUtils.isEmpty(smsCode)) {
            Map<Integer, String> smsMap = smsCodeMap();
            Integer smsCodeNum = Integer.valueOf(smsCode);
            if (smsMap.containsKey(smsCodeNum)) {
                return smsMap.get(smsCodeNum);
            }
        }
        /** 默认大陆的countryCode. */
        return "ZH";
    }
}
