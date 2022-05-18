package com.fjm.utils;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.apache.commons.lang3.RandomStringUtils;


/**
 *
 */
public class PasswordHandler {

    /**
     * @return 生成密码需要的盐
     */
    public static String getSalt() {
        return RandomStringUtils.randomAlphabetic(64);
    }

    /**
     * 加密
     *
     * @param salt
     * @param password
     * @return sha256
     */
    public static String encode(String salt, String password) {
        return Hashing.sha256().newHasher().putString(salt + password, Charsets.UTF_8).hash().toString();
    }

    /**
     * 校验
     *
     * @param rawPassword
     * @param encodedPassword
     * @param salt
     * @return
     */
    public static boolean match(String rawPassword, String encodedPassword, String salt) {
        if (encodedPassword.equals(encode(salt, rawPassword))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        String salt = getSalt();
        System.out.println(salt);
        String encodePassword = encode(salt, "123456");
        System.out.println(encodePassword);
        System.out.println(match("123456", encodePassword, salt));
    }
}
