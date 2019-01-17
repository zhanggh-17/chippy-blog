package com.loser.common.util;

public class NumUtil {

    /**
     * 随机生成6位A-Z和0-9之间的字符
     *
     * @return
     */
    public static String generateTempPassword() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean[] flags = new boolean[letters.length];
        char[] chs = new char[6];
        for (int i = 0; i < chs.length; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复
            // 将取到的字符放入字符数组
            chs[i] = letters[index];
            // 如果不重复就设置为true 供下次判断
            flags[index] = true;
        }
        return String.valueOf(chs);
    }

    /**
     * 随机生成10位A-Z和0-9之间的字符
     *
     * @return
     */
    public static String generateSign() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        boolean[] flags = new boolean[letters.length];
        char[] chs = new char[10];
        for (int i = 0; i < chs.length; i++) {
            int index;
            do {
                index = (int) (Math.random() * (letters.length));
            } while (flags[index]);// 判断生成的字符是否重复
            // 将取到的字符放入字符数组
            chs[i] = letters[index];
            // 如果不重复就设置为true 供下次判断
            flags[index] = true;
        }
        return String.valueOf(chs);
    }
}
