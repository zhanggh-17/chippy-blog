package com.loser.common.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
*@Description Base64 简单的编解码解码
*@Author chippy
*/
public class Base64Util {

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64(key);
    }

    @Test
    public void test() {
        byte[] s;
        try {
            s = encryptBASE64("123".getBytes());
            String s2 = new String(s);
            System.err.println(s2);

            byte[] s3 = decryptBASE64(s2);
            System.err.println(new String(s3));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
