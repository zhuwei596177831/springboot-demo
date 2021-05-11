package com.zhuweiwei.util;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author 朱伟伟
 * @date 2021-05-10 17:06:09
 * @description
 */
public class PasswordHelper {

    public static String md5(String source, String salt, int hashIterations) {
        return new SimpleHash(Md5Hash.ALGORITHM_NAME, source, salt, hashIterations).toHex();
    }

}
