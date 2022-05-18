package com.fjm.utils;


import com.alibaba.fastjson.JSONObject;
import shaded.org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5(String text) {
        // 加密后的字符串
        String encodeStr = DigestUtils.md5Hex(text);
        return encodeStr;
    }

    public static boolean verify(String text, String md5) {
        // 根据传入的密钥进行验证
        String md5Text = md5(text);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        System.out.println(md5("wrer"));
        System.out.println(md5("wrer").substring(0, 14));

        JSONObject deviceDiscoverJson = new JSONObject();
        deviceDiscoverJson.put("BSSID", "e129a5e1ba51a02766163b46b2855556");
        deviceDiscoverJson.put("Token", "66163b46b2855556");
        System.out.println(deviceDiscoverJson);
        String deviceDiscoverKey = MD5Utils.md5(deviceDiscoverJson.toJSONString()).substring(0, 14);
        System.out.println(deviceDiscoverKey);

    }

}
