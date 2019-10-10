package com.info.modules.sys.oauth2;

import com.info.exception.LiftException;
import com.info.string.UUIDUtils;

import java.security.MessageDigest;

/**
 * 功能描述: 
 *
 * @Params:  * @param null 
 * @Author:  Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:21
 * @Return: 
 */
public class TokenGenerator {

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    public static String generateValue() {
        return generateValue(UUIDUtils.getUUID32().toUpperCase());
    }

    public static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    public static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            throw new LiftException("生成Token失败", e);
        }
    }
}
